# jsondedup

To Build and Run the Application:
Requirements :
Java 8 or higher.
A Maven wrapper is provided with the source.

Building:
Run ./mvnw clean to clean out previous builds if any.
Run ./mvnw package to build and package an Uber  jar containing the application in the target folder.

Running the application:
Specify the following three properties in application.properties
to indicate the directory where the original JSON list and deduped
Json list should be read/written to, the original JSON file name and a comma seperated list of dedup strategies to use.
json.source.dir=C:\\adobe\\jsondedup\\src\\test\\samples
json.source.filename=testsample.json
dedup.strategies=email,id

A snippet from the JavaDocs for a Service Facade gives more details.

/**
	 * The directory where the source file containing JSON to be deduped is 		placed -
	 * injected from one of various Spring boot options such as
	 * application.properties, System properties , Cmd line args etc
	 **/
	@Value("${json.source.dir}")
	private String leadsSourceFileDir;

	/** The JSON file name with source data to be deduped **/
	@Value("${json.source.filename:leads.json}")
	private String leadsSourceFileName;

	/** The selected dedup strategies **/
	@Value("${dedup.strategies:id,email}")
	private String dedupStrategyNames;


Run the application using:
./mvnw spring-boot:run

Check in the json.source.dir for a file with name DEDUPED.json.source.filename containing the deduped list.

Some samples used for integration testing are available under src/test/samples.

Unit Tests are available under src/test/java - Email and Id based strategies have been unit tested.

Assumptions in working on the solution:
* The schema of the Lead object as depicted in the sample leads.json will be followed in the input data in that _id and email and entryDate will always be available in all leads.

If that's not true, after eliciting clarifications to the original requirements, handling could be coded for cases where one or more of the 3 fields of interest in de-duping (_id, email and entryDate) are empty or absent.

* The order of the De-duped list does not have to preserve the order of the leads in the original list.

* In the future, the Leads schema may change and additional fields of interest may be added to Leads. Some of these fields may be usable for de-duplication. The solution should be open for extension to allow additional strategies for de-duplication to be easily added. 
An example of such a potential future requirement is implemented via the First and last name based deduplication.

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


