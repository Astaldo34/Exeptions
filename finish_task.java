/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение,
что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение,
соответствующее типу проблемы.
Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class finish_task {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, DataFormatException {

        // Проверочные данные
        String test_1 = "Testov Test Testovich m 79101234455 01.02.1933";
        String test_2 = "Testov Bob Bobovich m 79876543214 10.12.1990";
        String test_3 = "Snow John Winters m 79001002030 01.01.3300";

        String[] userInputString = userInput();
        String writeString = "";
        for (int i = 0; i < userInputString.length; i++) {
            writeString += userInputString[i];
            writeString += " ";
        }
        writeString += "\n";

        //String fileName = "D:/Python/! New Lesson/Exeptions/finish_task_exeptions/BD/" + userInputString[0] + ".txt";
        String fileName = "BD/" + userInputString[0] + ".txt";
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(writeString);
        fileWriter.close();
        System.out.println("Файл успешно сохранён в " + fileName);
    }

    public static String[] userInput() throws DataFormatException, IOException {
        System.out.println("Введите данные, желательно в следующем порядке:\n" +
                "Фамилия, Имя, Отчество, Дата Рождения, Номер Телефона, Пол\n\n" +
                "Дата Рождения вводится в формате dd.mm.yyyy,\n\n" +
                "Номер телефона вводится в формате 79xxxxxxxxx,\n" +
                "где, xxxxxxxxxx - это девять цифр номера телефона.\n\n" +
                "Пол вводится формате одной латинской буквы f или m,\n" +
                "где f - женский, m - мужской.\n-->:");
        String userInput = scanner.nextLine();
        System.out.println(userInput);
        //String userInput = userInput;
        String[] correctUserInput = new String[6];
        String[] userInputArr = userInput.split(" ");

        while (userInputArr.length != 6) {
            throw new DataFormatException("Возможно, Неверное количество введённых данных.\n");
        }

        for (String check : userInputArr) {
            if (correctUserInput[0] == null || correctUserInput[0].equals("")) {
                correctUserInput[0] = checkedSurname(check);
                if (correctUserInput[0] != null && !correctUserInput[0].isEmpty())
                    continue;
            }

            if (correctUserInput[1] == null || correctUserInput[1].equals("")) {
                correctUserInput[1] = checkedName(check);
                if (correctUserInput[1] != null && !correctUserInput[1].isEmpty())
                    continue;
            }

            if (correctUserInput[2] == null || correctUserInput[2].equals("")) {
                correctUserInput[2] = checkedSecondname(check);
                if (correctUserInput[2] != null && !correctUserInput[2].isEmpty())
                    continue;
            }

            if (correctUserInput[3] == null || correctUserInput[3].equals("")) {
                if (check.length() == 10 && check.contains(".")) {
                    correctUserInput[3] = checkedBirthdate(check);
                }
            }

            if (correctUserInput[4] == null || correctUserInput[4].equals("")) {
                if (check.length() == 11 && check.contains("79")) {
                    correctUserInput[4] = checkedPhoneNumber(check);
                }
            }

            if (correctUserInput[5] == null || correctUserInput[5].equals("")) {
                if (check.length() == 1) {
                    correctUserInput[5] = checkedGender(check);
                }
            }
        }

        for (String check : correctUserInput) {
            if (check == null || check.equals(""))
                throw new DataFormatException("Ошибка при обработке данных.");
        }

        return correctUserInput;
    }

    public static String checkedSurname(String surname) {
        String resSurname = "";
        String answer = "";
        while (true) {
            System.out.print(surname + " - это фамилия?\n Y/y (да) или N/n (нет) : ");
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("y")) {
                resSurname = surname;
                break;
            } else if (answer.equals("n")) {
                break;
            } else {
                System.out.println("Неверный формат ответа.");
            }
        }
        return resSurname;
    }

    public static String checkedName(String name) {
        String resName = "";
        String answer = "";
        System.out.println("Не могу разобраться, нужна помощь.");
        while (true) {
            System.out.print(name + " - это имя?\n Y/y (да) или N/n (нет) : ");
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("y")) {
                resName = name;
                break;
            } else if (answer.equals("n")) {
                break;
            } else {
                System.out.println("Неверный формат ответа.");
            }
        }
        return resName;
    }

    public static String checkedSecondname(String secondname) {
        String resSecondname = "";
        String answer = "";
        System.out.println("Не могу разобраться, нужна помощь.");
        while (true) {
            System.out.print(secondname + " - это отчество?\n Y/y (да) или N/n (нет) : ");
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("y")) {
                resSecondname = secondname;
                break;
            } else if (answer.equals("n")) {
                break;
            } else {
                System.out.println("Неверный формат ответа.");
            }
        }
        return resSecondname;
    }

    public static String checkedBirthdate(String birthdate) throws DataFormatException {
        try {
            LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException ex) {
            throw new DataFormatException("Неверный формат даты рождения", ex);
        }
        return birthdate;
    }

    public static String checkedPhoneNumber(String phoneNumber) throws DataFormatException {
        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException ex) {
            throw new DataFormatException("Неверный формат номера телефона", ex);
        }
        return phoneNumber.toString();
    }

    public static String checkedGender(String gender) throws DataFormatException {
        char charGender;
        gender = gender.toLowerCase();
        charGender = gender.charAt(0);
        if (charGender == 'f' || charGender == 'm') {
            return String.valueOf(charGender);
        } else {
            throw new DataFormatException("Неверное значение пола.");
        }
    }

    static class DataFormatException extends Exception {
        public DataFormatException(String message) {
            super(message);
        }

        public DataFormatException (String message, Throwable ex) {
            super(message, ex);
        }
    }
}
