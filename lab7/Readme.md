Лабораторная работа №7
Задание на лабораторную работу
В процессе написания тестовых заданий ознакомиться с элементами языка XML и средствами Java для работы с XML-документами, а также с созданием html-страницы с применением CSS.

Задание 1
Изучить предлагаемый файл описания типа документа сформировать документ согласно этим правилам.
---student.dtd ---------------------------------------------------
<?xml version="1.0" encoding="UTF-8" ?>

<!ELEMENT student (subject*,average?)>
<!ATTLIST student
lastname CDATA #REQUIRED
>

<!ELEMENT subject EMPTY>
<!ATTLIST subject
 title CDATA #REQUIRED
 mark (1|2|3|4|5) #REQUIRED
>

<!ELEMENT average (#PCDATA)>
------------------------------------------------------------------
Задание 2
Разработать на Java консольное приложение, имеющее два входных параметра: имена входного и выходного файла. Задача приложения заключается в проверке значения средней оценки и его коррекции, если в исходном документе оно не соответствует действительности. Использовать DOM - анализатор.
Задание 3
Разработать html-страницы для ввода данных и для вывода результата.
Первая страница должна называться index.html. На ней должны находиться:
•	заголовок,
•	сопроводительный текст,
•	два поля для ввода данных для выполнения операции,
•	приглашения (подсказки) к этим полям ввода,
•	радиокнопки с четырьмя арифметическими операциями,
•	кнопка для завершения ввода (с переходом на result.html).
Вторая страница должна называться result.html. На ней должны находиться:
•	заголовок,
•	значения аргументов и результат выполнения операции, оформленные в виде таблицы,
•	ваша любимая фотография,
•	ссылка на исходную страницу, меняющая цвет при наведении на неё курсора.
Обе страницы должны быть оформлены с применением CSS. Таблица стилей (общая для обеих страниц) и код на JavaScript должны быть размещены в отдельных файлах. Все элементы обеих страниц должны изменить свой внешний вид в соответствии со стилями (дизайн – на ваше усмотрение).
