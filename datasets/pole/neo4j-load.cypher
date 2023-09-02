return 'Loading nodes' as comment;

load csv with headers from 'file:///datasets/pole/pole.nodes.Area.csv' as row
create (a:Area)
set
a.id=row.`:ID`,
a.areaCode=row.areaCode
return 'Area', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Crime.csv' as row
create (n:Crime)
set n.id=row.`:ID`,
n.date=row.date,
n.note=row.note,
n.last_outcome=row.last_outcome,
n.type=row.type
return 'Crime', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Email.csv' as row
create (:Email {id: row.`:ID`, email_address: row.email_address})
return 'Email', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Location.csv' as row
create (n:Location)
set n.id=row.`:ID`,
n.address=row.address,
n.latitude=toFloat(row.`latitude:double`),
n.longitude=toFloat(row.`longitude:double`),
n.postcode=row.postcode
return 'Location', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Object.csv' as row
create (n:Object)
set n.id=row.`:ID`,
n.description=row.description,
n.code=row.id,
n.type=row.type
return 'Object', count(*);


load csv with headers from 'file:///datasets/pole/pole.nodes.Officer.csv' as row
create (n:Officer)
set n.id=row.`:ID`,
n.badge_no=row.badge_no,
n.name=row.name,
n.surname=row.surname,
n.rank=row.rank
return 'Officer', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Person.csv' as row
create (n:Person)
set n.id=row.`:ID`,
n.nhs_no=row.nhs_no,
n.name=row.name,
n.surname=row.surname,
n.age=row.age
return 'Person', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Phone.csv' as row
create (n:Phone {id: row.`:ID`, phone: row.phoneNo})
return 'Phone', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.PhoneCall.csv' as row
create (n:PhoneCall {id: row.`:ID`})
set n.call_date=row.call_date,
n.call_time=row.call_time,
n.call_type=row.call_type,
n.call_duration=row.call_duration
return 'PhoneCall', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.PostCode.csv' as row
create (n:PostCode {id: row.`:ID`, code: row.code})
return 'PostCode', count(*);

load csv with headers from 'file:///datasets/pole/pole.nodes.Vehicle.csv' as row
create (n:Vehicle {id: row.`:ID`})
set n.reg=row.reg,
n.year=row.year,
n.mode=row.model,
n.make=row.make
return 'Vehicle', count(*);

return 'Creating node key constraints' as comment;

create constraint for (n:Area) require (n.id) is node key;
create constraint for (n:Crime) require (n.id) is node key;
create constraint for (n:Email) require (n.id) is node key;
create constraint for (n:Location) require (n.id) is node key;
create constraint for (n:Object) require (n.id) is node key;
create constraint for (n:Officer) require (n.id) is node key;
create constraint for (n:Person) require (n.id) is node key;
create constraint for (n:Phone) require (n.id) is node key;
create constraint for (n:PhoneCall) require (n.id) is node key;
create constraint for (n:PostCode) require (n.id) is node key;
create constraint for (n:Vehicle) require (n.id) is node key;

return 'Creating relationship' as comment;

load csv with headers from 'file:///datasets/pole/pole.relationships.CALLED.csv' as row
match (n1:PhoneCall {id: row.`:START_ID`}), (n2:Phone {id: row.`:END_ID`})
create (n1)-[:CALLED]->(n2)
return 'CALLED', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.CALLER.csv' as row
match (n1:PhoneCall {id: row.`:START_ID`}), (n2:Phone {id: row.`:END_ID`})
merge (n1)-[:CALLER]->(n2)
return 'CALLER', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.CURRENT_ADDRESS.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Location {id: row.`:END_ID`})
merge (n1)-[:CURRENT_ADDRESS]->(n2)
return 'CURRENT_ADDRESS', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.PARTY_TO.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Crime {id: row.`:END_ID`})
merge (n1)-[:PARTY_TO]->(n2)
return 'PARTY_TO', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.FAMILY_REL.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Person {id: row.`:END_ID`})
merge (n1)-[r:FAMILY_REL]->(n2)
set r.rel_type=row.rel_type
return 'FAMILY_REL', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.KNOWS.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Person {id: row.`:END_ID`})
merge (n1)-[r:KNOWS]->(n2)
set r.rel_type=row.rel_type
return 'KNOWS', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.KNOWS_LW.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Person {id: row.`:END_ID`})
merge (n1)-[r:KNOWS_LW]->(n2)
set r.rel_type=row.rel_type
return 'KNOWS_LW', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.KNOWS_PHONE.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Person {id: row.`:END_ID`})
merge (n1)-[r:KNOWS_PHONE]->(n2)
set r.rel_type=row.rel_type
return 'KNOWS_PHONE', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.KNOWS_SN.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Person {id: row.`:END_ID`})
merge (n1)-[r:KNOWS_SN]->(n2)
set r.rel_type=row.rel_type
return 'KNOWS_SN', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.HAS_EMAIL.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Email {id: row.`:END_ID`})
merge (n1)-[:HAS_EMAIL]->(n2)
return 'HAS_EMAIL', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.HAS_PHONE.csv' as row
match (n1:Person {id: row.`:START_ID`}), (n2:Phone {id: row.`:END_ID`})
merge (n1)-[:HAS_PHONE]->(n2)
return 'HAS_PHONE', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.HAS_POSTCODE.csv' as row
match (n1:Location {id: row.`:START_ID`}), (n2:PostCode {id: row.`:END_ID`})
merge (n1)-[:HAS_POSTCODE]->(n2)
return 'HAS_POSTCODE', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.LOCATION_IN_AREA.csv' as row
match (n1:Location {id: row.`:START_ID`}), (n2:Area {id: row.`:END_ID`})
merge (n1)-[:LOCATION_IN_AREA]->(n2)
return 'LOCATION_IN_AREA', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.POSTCODE_IN_AREA.csv' as row
match (n1:PostCode {id: row.`:START_ID`}), (n2:Area {id: row.`:END_ID`})
merge (n1)-[:POSTCODE_IN_AREA]->(n2)
return 'POSTCODE_IN_AREA', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.INVESTIGATED_BY.csv' as row
match (n1:Crime {id: row.`:START_ID`}), (n2:Officer {id: row.`:END_ID`})
merge (n1)-[:INVESTIGATED_BY]->(n2)
return 'INVESTIGATED_BY', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.OCCURRED_AT.csv' as row
match (n1:Crime {id: row.`:START_ID`}), (n2:Location {id: row.`:END_ID`})
merge (n1)-[:OCCURRED_AT]->(n2)
return 'OCCURRED_AT', count(*);

load csv with headers from 'file:///datasets/pole/pole.relationships.INVOLVED_IN.csv' as row
match (n1:Object {id: row.`:START_ID`}), (n2:Crime {id: row.`:END_ID`})
merge (n1)-[:INVOLVED_IN]->(n2)
return 'INVOLVED_IN', count(*);

return 'Creating additional indexes' as comment;

create index for (n:Location) ON (n.address);

match (n)
return LABELS(n)[0] as label, count(*)
order by label;

match ()-[r]->()
return TYPE(r) as type, count(*)
order by type;

match ()
return 'All nodes', count(*);

match ()-[]->()
return 'All relationships', count(*);
