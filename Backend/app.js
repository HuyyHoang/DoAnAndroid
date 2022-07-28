const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const app = express();

var corsOptions = {
  origin: "http://localhost:8080"
};

app.use(cors(corsOptions));

// parse requests of content-type - application/json
app.use(bodyParser.json());

// parse requests of content-type - application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// database
const db = require("./app/models");
const Category = db.category;
const Bank = db.bank;

// db.sequelize.sync() 
// ------ if want to reset database, use this command
// ------ it will delete all table if duplicate name and recreate those tables;
// force: true will drop the table if it already exists

/*
db.sequelize.sync({force: true}).then(() => {
  console.log('Drop and reCreate Table!');
  initial();
});
*/

// First Route
app.get("/", (req, res) => {
  res.json({ message: "Welcome to GROUP 17 - UIT." });
});

// routes
require('./app/routes/auth.routes')(app);
require('./app/routes/user.routes')(app);

// set port, listen for requests
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});

function initial() {
  
  // -------------- Expense --------------
  Category.create({
    id: 1,
    name: "Housing",
    description: "Expense"

  });

  Category.create({
    id: 2,
    name: "Food & Drink",
    description: "Expense"
  });
  
  Category.create({
    id: 3,
    name: "Education",
    description: "Expense"
  });

  Category.create({
    id: 4,
    name: "Transportation",
    description: "Expense"
  });

  Category.create({
    id: 5,
    name: "Utilities",
    description: "Expense"
  });

  Category.create({
    id: 6,
    name: "Insurance",
    description: "Expense"
  });

  Category.create({
    id: 7,
    name: "Medical & Healthcare",
    description: "Expense"
  });

  Category.create({
    id: 8,
    name: "Investment",
    description: "Expense"
  });
  
  Category.create({
    id: 9,
    name: "Debt",
    description: "Expense"
  });

  Category.create({
    id: 10,
    name: "Personal",
    description: "Expense"
  });

  Category.create({
    id: 11,
    name: "Entertainment",
    description: "Expense"
  });

  Category.create({
    id: 12,
    name: "Clothing",
    description: "Expense"
  });

  // -------------- Income --------------
  Category.create({
    id: 13,
    name: "Salary",
    description: "Income"
  });

  Category.create({
    id: 14,
    name: "Gift & Donation",
    description: "Income"
  });

  Category.create({
    id: 15,
    name: "Loan",
    description: "Income"
  });
  
  Category.create({
    id: 16,
    name: "Scholarship",
    description: "Income"
  });
  
  Category.create({
    id: 17,
    name: "Investment",
    description: "Income"
  });
  
  Category.create({
    id: 18,
    name: "Lottery",
    description: "Income"
  });
  

  // -------------- Bank --------------
  Bank.create({
    id: 1,
    name: "ACB"
  });

  Bank.create({
    id: 2,
    name: "BIDV"
  });
  
  Bank.create({
    id: 3,
    name: "Vietinbank"
  });
  
  Bank.create({
    id: 4,
    name: "MB"
  });
  
  Bank.create({
    id: 5,
    name: "ABBank"
  });
  
  Bank.create({
    id: 6,
    name: "Wallet"
  });
  
  Bank.create({
    id: 7,
    name: "Paypal"
  });

  Bank.create({
    id: 8,
    name: "TPBank"
  });
  
  Bank.create({
    id: 9,
    name: "DongA Bank"
  });

  Bank.create({
    id: 10,
    name: "BacABank"
  });  

  Bank.create({
    id: 11,
    name: "SeABank"
  }); 

  Bank.create({
    id: 12,
    name: "MSB"
  }); 

  Bank.create({
    id: 13,
    name: "Techcombank"
  }); 

  Bank.create({
    id: 14,
    name: "Sacombank"
  }); 

  Bank.create({
    id: 15,
    name: "NamABank"
  });

  Bank.create({
    id: 16,
    name: "VCB"
  });

  Bank.create({
    id: 17,
    name: "HDBank"
  });

  Bank.create({
    id: 18,
    name: "OCB"
  });

  Bank.create({
    id: 19,
    name: "PVcombank"
  });

  Bank.create({
    id: 20,
    name: "VPBank"
  });
}
