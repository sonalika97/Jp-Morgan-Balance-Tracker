# Bank Account Balance Tracker (Spring Boot + React)

This project is a small end-to-end system that simulates how a bank might process
incoming transactions and show the account balance to the user.  
I built it as part of a technical exercise to demonstrate:

 handling transactions safely,
 keeping only the most recent 1000 transactions,
 creating audit batches
 and showing everything in a simple UI.

The project has three parts:
1. Backend (Spring Boot) - processes credits/debits and exposes REST APIs  
2. Frontend (React) - a dashboard that displays the live balance  
3. Producer (Java) - a small script that generates random transactions

Everything runs locally and works together.



What the system does

Processes transactions  
Each transaction has an ID and an amount(positive = credit, negative = debit).  
The backend updates the balance and stores the transaction.

Keeps only the last 1000 transactions  
To avoid unbounded memory usage, older transactions are removed once the count crosses 1000.  
This rolling window is one of the main requirements.

Creates audit batches  
When /audit/submit is called, the backend groups the last 1000 transactions
into multiple batches.  
Each batch must not exceed £1,000,000 in total value.

Provides a simple API  
transaction - submit a new transaction  
balance - check the current balance and totals  
audit - view the last 1000 transactions  
audit/submit - generate audit batches  

Has a small banking-style UI  
The React page refreshes automatically and shows:
- account details,
- net balance,
- total credits,
- total debits,
- number of processed transactions,
- how many are currently stored.

The layout is intentionally simple but looks like a basic bank dashboard.


Project Structure

final-submission/
│
├── balance-tracker/ (Spring Boot backend)
├── balance-ui/ (React dashboard UI)
├── JPMorgan/ (Java producer)
└── README.md


1. Start the backend
cd balance-tracker
mvn spring-boot:run

nginx
Copy code
The backend will run on:
http://localhost:8080

shell
Copy code

2. Run the transaction producer (optional but useful)
cd JPMorgan
javac Producer.java
java Producer

pgsql
Copy code
It will continuously send both credit and debit transactions to the backend.

3. Start the UI
cd balance-ui
npm install
npm start



The UI opens here:
http://localhost:3000




 Example API Response (/balance)

```json
{
  "netBalance": 1520.50,
  "totalCredits": 20000.00,
  "totalDebits": -18479.50,
  "totalTransactionsProcessed": 1450,
  "last1000Stored": 1000
}



It shows the ability to:

build a full stack application from scratch,

understand data limits (rolling buffer),

work with REST APIs,

write simple batching logic,

and connect backend + UI + a separate producer program.
