RETURN 'Loading nodes' AS comment;

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Bank.csv' AS row
CALL {
  WITH row
  CREATE (n:Bank)
  SET n._id = row._id, n.id = row.id, n.name = row.name
} IN TRANSACTIONS
RETURN 'Bank', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Client.csv' AS row
CALL {
  WITH row
  CREATE (n:Client)
  SET n._id = row._id, n.id = row.id, n.name = row.name
} IN TRANSACTIONS
RETURN 'Client', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Email.csv' AS row
CALL {
  WITH row
  CREATE (n:Email)
  SET n._id = row._id, n.email = row.email
} IN TRANSACTIONS
RETURN 'Email', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Merchant.csv' AS row
CALL {
  WITH row
  CREATE (n:Merchant)
  SET n._id = row._id, n.id = row.id, n.name = row.name
} IN TRANSACTIONS
RETURN 'Merchant', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Phone.csv' AS row
CALL {
  WITH row
  CREATE (n:Phone)
  SET n._id = row._id, n.phoneNumber = row.phoneNumber
} IN TRANSACTIONS
RETURN 'Phone', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/SSN.csv' AS row
CALL {
  WITH row
  CREATE (n:SSN)
  SET n._id = row._id, n.ssn = row.ssn
} IN TRANSACTIONS
RETURN 'SSN', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Client_Mule.csv' AS row
CALL {
WITH row
CREATE (n:Client:Mule)
SET n._id = row._id, n.id = row.id, n.name = row.name
} IN TRANSACTIONS
RETURN 'Mule', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/CashIn_Transaction.csv' AS row
CALL {
  WITH row
  CREATE (n:CashIn:Transaction)
  SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
  n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
  n.globalStep=toInteger(row.globalStep)
} IN TRANSACTIONS
RETURN 'CashIn,Transaction', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/CashOut_Transaction.csv' AS row
CALL {
  WITH row
  CREATE (n:CashOut:Transaction)
  SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
  n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
  n.globalStep=toInteger(row.globalStep)
} IN TRANSACTIONS
RETURN 'CashOut,Transaction', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Debit_Transaction.csv' AS row
CALL {
  WITH row
  CREATE (n:Debit:Transaction)
  SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
  n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
  n.globalStep=toInteger(row.globalStep)
} IN TRANSACTIONS
RETURN 'Debit,Transaction', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Payment_Transaction.csv' AS row
CALL {
  WITH row
  CREATE (n:Payment:Transaction)
  SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
  n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
  n.globalStep=toInteger(row.globalStep)
} IN TRANSACTIONS
RETURN 'Payment,Transaction', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/Transfer_Transaction.csv' AS row
CALL {
  WITH row
  CREATE (n:Transfer:Transaction)
  SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
  n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
  n.globalStep=toInteger(row.globalStep)
} IN TRANSACTIONS
RETURN 'Transfer,Transaction', COUNT(*);


RETURN 'Creating node key constraints' AS comment;

create constraint for (n:Bank) require (n._id) is node key;
create constraint for (n:CashIn) require (n._id) is node key;
create constraint for (n:Transaction) require (n._id) is node key;
create constraint for (n:CashOut) require (n._id) is node key;
create constraint for (n:Client) require (n._id) is node key;
create constraint for (n:Mule) require (n._id) is node key;
create constraint for (n:Debit) require (n._id) is node key;
create constraint for (n:Email) require (n._id) is node key;
create constraint for (n:Merchant) require (n._id) is node key;
create constraint for (n:Payment) require (n._id) is node key;
create constraint for (n:SSN) require (n._id) is node key;
create constraint for (n:Transfer) require (n._id) is node key;

RETURN 'Creating relationship' AS comment;

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_FIRST_TX.csv' AS row
CALL {
  WITH row
  MATCH (a:Client), (b:Transaction)
  WHERE a._id=row._start AND b._id=row._end
  MERGE (a)-[r:FIRST_TX]->(b)
} IN TRANSACTIONS
RETURN 'FIRST_TX', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_LAST_TX.csv' AS row
CALL {
WITH row
MATCH (a:Client), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:LAST_TX]->(b)
} IN TRANSACTIONS
RETURN 'LAST_TX', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_NEXT.csv' AS row
CALL {
WITH row
MATCH (a:Transaction), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:NEXT]->(b)
} IN TRANSACTIONS
RETURN 'NEXT', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_PERFORMED.csv' AS row
CALL {
WITH row
MATCH (a:Client), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:PERFORMED]->(b)
} IN TRANSACTIONS
RETURN 'PERFORMED', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_TO.csv' AS row
CALL {
WITH row
MATCH (a:Transaction), (b:Client|Merchant|Bank)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:TO]->(b)
} IN TRANSACTIONS
RETURN 'TO', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_HAS_EMAIL.csv' AS row
CALL {
WITH row
MATCH (a:Client), (b:Email)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:HAS_EMAIL]->(b)
} IN TRANSACTIONS
RETURN 'HAS_EMAIL', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_HAS_PHONE.csv' AS row
CALL {
WITH row
MATCH (a:Client), (b:Phone)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:HAS_PHONE]->(b)
} IN TRANSACTIONS
RETURN 'HAS_PHONE', COUNT(*);

LOAD CSV WITH HEADERS FROM 'file:///datasets/fraud/relationship_HAS_SSN.csv' AS row
CALL {
WITH row
MATCH (a:Client), (b:SSN)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:HAS_SSN]->(b)
} IN TRANSACTIONS
RETURN 'HAS_SSN', COUNT(*);


MATCH (n)
RETURN labels(n)[0] AS label, COUNT(*)
ORDER BY label;

MATCH ()-[r]->()
RETURN type(r) AS type, COUNT(*)
ORDER BY type;

MATCH ()
RETURN 'All nodes', COUNT(*);

MATCH ()-[]->()
RETURN 'All relationships', COUNT(*);
