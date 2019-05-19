# jsondedup

To Run the code:
Requirements :
Java 8 or higher.
A Maven wrapper is provided with the source.



Original Requirements:
Take a variable number of identically structured json records and de-duplicate the set.

 An example file of records is given in the accompanying 'leads.json'. Output should be same format, with dups reconciled according to the following rules:

 1. The data from the newest date should be preferred

2. duplicate IDs count as dups. Duplicate emails count as dups. Both must be unique in our dataset. Duplicate values elsewhere do not count as dups.

3. If the dates are identical the data from the record provided last in the list should be preferred

 Simplifying assumption: the program can do everything in memory (don't worry about large files)

 The application should also provide a log of changes including some representation of the source record, the output record and the individual field changes (value from and value to) for each field.

 Please implement as a command-line java program, or a javascript program.
 
 leads.json example:
 {"leads":[
{
"_id": "jkj238238jdsnfsj23",
"email": "foo@bar.com",
"firstName":  "John",
"lastName": "Smith",
"address": "123 Street St",
"entryDate": "2014-05-07T17:30:20+00:00"
},
{
"_id": "edu45238jdsnfsj23",
"email": "mae@bar.com",
"firstName":  "Ted",
"lastName": "Masters",
"address": "44 North Hampton St",
"entryDate": "2014-05-07T17:31:20+00:00"
},
{
"_id": "wabaj238238jdsnfsj23",
"email": "bog@bar.com",
"firstName":  "Fran",
"lastName": "Jones",
"address": "8803 Dark St",
"entryDate": "2014-05-07T17:31:20+00:00"
},
{
"_id": "jkj238238jdsnfsj23",
"email": "coo@bar.com",
"firstName":  "Ted",
"lastName": "Jones",
"address": "456 Neat St",
"entryDate": "2014-05-07T17:32:20+00:00"
},
{
"_id": "sel045238jdsnfsj23",
"email": "foo@bar.com",
"firstName":  "John",
"lastName": "Smith",
"address": "123 Street St",
"entryDate": "2014-05-07T17:32:20+00:00"
},
{
"_id": "qest38238jdsnfsj23",
"email": "foo@bar.com",
"firstName":  "John",
"lastName": "Smith",
"address": "123 Street St",
"entryDate": "2014-05-07T17:32:20+00:00"
},
{
"_id": "vug789238jdsnfsj23",
"email": "foo1@bar.com",
"firstName":  "Blake",
"lastName": "Douglas",
"address": "123 Reach St",
"entryDate": "2014-05-07T17:33:20+00:00"
},
{
"_id": "wuj08238jdsnfsj23",
"email": "foo@bar.com",
"firstName":  "Micah",
"lastName": "Valmer",
"address": "123 Street St",
"entryDate": "2014-05-07T17:33:20+00:00"
},
{
"_id": "belr28238jdsnfsj23",
"email": "mae@bar.com",
"firstName":  "Tallulah",
"lastName": "Smith",
"address": "123 Water St",
"entryDate": "2014-05-07T17:33:20+00:00"
},
{
"_id": "jkj238238jdsnfsj23",
"email": "bill@bar.com",
"firstName":  "John",
"lastName": "Smith",
"address": "888 Mayberry St",
"entryDate": "2014-05-07T17:33:20+00:00"
}]
}


