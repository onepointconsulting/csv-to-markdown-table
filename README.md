# csv-to-markdown-table
This project provides a simple function which converts CSV to a Markdown table. Typically the resulting table will look like this:

```
| table                          | insertedRecords  | start                | end                  | duration      | errors  | 
|--------------------------------|------------------|----------------------|----------------------|---------------|---------| 
| acl_object_identity            | 731              | 2022-08-09T13:30:28  | 2022-08-09T13:30:28  | 0 seconds     | 0       | 
| acl_permission                 | 2487             | 2022-08-09T13:30:31  | 2022-08-09T13:30:31  | 0 seconds     | 0       | 
| authorities                    | 740              | 2022-08-09T13:30:34  | 2022-08-09T13:30:34  | 0 seconds     | 0       | 
| tb_action                      | 3                | 2022-08-09T13:30:36  | 2022-08-09T13:30:36  | 0 seconds     | 0       | 
| tb_action_target               | 1                | 2022-08-09T13:30:38  | 2022-08-09T13:30:38  | 0 seconds     | 0       | 
| tb_announcement                | 1                | 2022-08-09T13:30:40  | 2022-08-09T13:30:40  | 0 seconds     | 0       | 
| tb_channel                     | 7                | 2022-08-09T13:30:43  | 2022-08-09T13:30:43  | 0 seconds     | 0       | 
| tb_country                     | 114              | 2022-08-09T13:30:46  | 2022-08-09T13:30:46  | 0 seconds     | 0       | 
| tb_domain                      | 17               | 2022-08-09T13:30:48  | 2022-08-09T13:30:48  | 0 seconds     | 0       | 
| tb_domain_category             | 2                | 2022-08-09T13:30:51  | 2022-08-09T13:30:51  | 0 seconds     | 0       | 
| tb_email                       | 542              | 2022-08-09T13:30:53  | 2022-08-09T13:30:53  | 0 seconds     | 0       | 
| tb_email_copy                  | 153              | 2022-08-09T13:30:55  | 2022-08-09T13:30:55  | 0 seconds     | 0       | 
| tb_event                       | 53768            | 2022-08-09T13:31:04  | 2022-08-09T13:31:43  | 39 seconds    | 0       | 
```

The main function can be called like so:

```Java
CsvToMarkdown.convertCsvToMarkDown(csvLines, "\t", false);
```

The first argument is a `String` with the CSV content, then the separator and a `boolean` parameter that determines whether the header is printed or not.

## Build

In order to build the project you will need to have at least Java 8 and Maven installed.

The build command is:

```
mvn clean package
```
