RETURN 'Loading nodes' AS comment;

LOAD CSV FROM '/datasets/fraud/Bank.csv' WITH HEADER AS row
CREATE (n:Bank)
SET n._id = row._id, n.id = row.id, n.name = row.name
RETURN 'Bank', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Client.csv' WITH HEADER AS row
CREATE (n:Client)
SET n._id = row._id, n.id = row.id, n.name = row.name
RETURN 'Client', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Email.csv' WITH HEADER AS row
CREATE (n:Email)
SET n._id = row._id, n.email = row.email
RETURN 'Email', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Merchant.csv' WITH HEADER AS row
CREATE (n:Merchant)
SET n._id = row._id, n.id = row.id, n.name = row.name
RETURN 'Merchant', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Phone.csv' WITH HEADER AS row
CREATE (n:Phone)
SET n._id = row._id, n.phoneNumber = row.phoneNumber
RETURN 'Phone', COUNT(*);

LOAD CSV FROM '/datasets/fraud/SSN.csv' WITH HEADER AS row
CREATE (n:SSN)
SET n._id = row._id, n.ssn = row.ssn
RETURN 'SSN', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Client_Mule.csv' WITH HEADER AS row
CREATE (n:Client:Mule)
SET n._id = row._id, n.id = row.id, n.name = row.name
RETURN 'Mule', COUNT(*);

LOAD CSV FROM '/datasets/fraud/CashIn_Transaction.csv' WITH HEADER AS row
CREATE (n:CashIn:Transaction)
SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
    n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
    n.globalStep=toInteger(row.globalStep)
RETURN 'CashIn,Transaction', COUNT(*);

LOAD CSV FROM '/datasets/fraud/CashOut_Transaction.csv' WITH HEADER AS row
CREATE (n:CashOut:Transaction)
SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
    n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
    n.globalStep=toInteger(row.globalStep)
RETURN 'CashOut,Transaction', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Debit_Transaction.csv' WITH HEADER AS row
CREATE (n:Debit:Transaction)
SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
    n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
    n.globalStep=toInteger(row.globalStep)
RETURN 'Debit,Transaction', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Payment_Transaction.csv' WITH HEADER AS row
CREATE (n:Payment:Transaction)
SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
    n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
    n.globalStep=toInteger(row.globalStep)
RETURN 'Payment,Transaction', COUNT(*);

LOAD CSV FROM '/datasets/fraud/Transfer_Transaction.csv' WITH HEADER AS row
CREATE (n:Transfer:Transaction)
SET n._id = row._id, n.amount = toFloat(row.amount), n.fraud=toBoolean(row.fraud),
    n.id=row.id, n.step=toInteger(row.step), n.ts=toInteger(row.ts),
    n.globalStep=toInteger(row.globalStep)
RETURN 'Transfer,Transaction', COUNT(*);

return 'Indices for labels' as comment;
create index on :Bank;
create index on :CashIn;
create index on :CashOut;
create index on :Debit;
create index on :Payment;
create index on :Transfer;
create index on :Transaction;
create index on :Client;
create index on :Mule;
create index on :Merchant;
create index on :Email;
create index on :Phone;
create index on :SSN;

RETURN 'Creating node key constraints' AS comment;
create index on :Bank(_id);
create index on :CashIn(_id);
create index on :CashOut(_id);
create index on :Debit(_id);
create index on :Payment(_id);
create index on :Transfer(_id);
create index on :Transaction(_id);
create index on :Client(_id);
create index on :Mule(_id);
create index on :Merchant(_id);
create index on :Email(_id);
create index on :Phone(_id);
create index on :SSN(_id);



RETURN 'Creating relationship' AS comment;

LOAD CSV FROM '/datasets/fraud/relationship_FIRST_TX.csv' WITH HEADER AS row
MATCH (a:Client), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:FIRST_TX]->(b)
RETURN 'FIRST_TX', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_LAST_TX.csv' WITH HEADER AS row
MATCH (a:Client), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:LAST_TX]->(b)
RETURN 'LAST_TX', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_NEXT.csv' WITH HEADER AS row
MATCH (a:Transaction), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:NEXT]->(b)
RETURN 'NEXT', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_PERFORMED.csv' WITH HEADER AS row
MATCH (a:Client), (b:Transaction)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:PERFORMED]->(b)
RETURN 'PERFORMED', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_TO.csv' WITH HEADER AS row
MATCH (b:Client {_id: row._end}), (a:Transaction {_id: row._start})
MERGE (a)-[r:TO]->(b)
RETURN 'TO', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_TO.csv' WITH HEADER AS row
MATCH (b:Bank {_id: row._end}), (a:Transaction {_id: row._start})
MERGE (a)-[r:TO]->(b)
RETURN 'TO', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_TO.csv' WITH HEADER AS row
MATCH (b:Merchant {_id: row._end}), (a:Transaction {_id: row._start})
MERGE (a)-[r:TO]->(b)
RETURN 'TO', COUNT(*);


LOAD CSV FROM '/datasets/fraud/relationship_HAS_EMAIL.csv' WITH HEADER AS row
MATCH (a:Client), (b:Email)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:HAS_EMAIL]->(b)
RETURN 'HAS_EMAIL', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_HAS_PHONE.csv' WITH HEADER AS row
MATCH (a:Client), (b:Phone)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:HAS_PHONE]->(b)
RETURN 'HAS_PHONE', COUNT(*);

LOAD CSV FROM '/datasets/fraud/relationship_HAS_SSN.csv' WITH HEADER AS row
MATCH (a:Client), (b:SSN)
WHERE a._id=row._start AND b._id=row._end
MERGE (a)-[r:HAS_SSN]->(b)
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
