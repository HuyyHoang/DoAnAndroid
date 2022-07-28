const db = require("../models");
const Category = db.category;
const User = db.user;
const Transaction = db.transaction;
const Account = db.account;
const Bank = db.bank;
const Transfer = db.transfer;
const Budget = db.budget;
const { Op } = require("sequelize");
const { Sequelize, sequelize, transfer, budget } = require("../models");
const { json } = require("body-parser");

exports.userBoard = (req, res) => {
  return res.status(200).send({
    message: "Success"})
};

exports.getListExpense = (req, res) => {
  Category.findAll({
    attributes: ['name'],
    where: {
      description: "Expense"
    }
  })
  .then(function (list) {
    res.status(200).json(list);
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  });
};

exports.getListIncome = (req, res) => {
  Category.findAll({
    attributes: ['name'],
    where: {
      description: "Income"
    }
  })
  .then(function (list) {
    res.status(200).json(list);
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  });
};

exports.createIncome = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.name
      }
    })
    .then(account => {
      Account.update({amount: account.amount += req.body.amount}, {
        where: {
          id: account.id
        }
      })

      Category.findOne({
        where: {
          name: req.body.category_name
        }
      })
      .then(category => {
        var dateObj = new Date(Date.now());
        var month = dateObj.getMonth() + 1;
        var day = dateObj.getDate();
        var year = dateObj.getFullYear();

        var newdate = day + "/" + month + "/" + year;

        var hour = dateObj.getHours();
        var minute = dateObj.getMinutes();
        if (minute < 10) {
          minute = "0" + minute
        }

        var time = hour + ":" + minute;

        Transaction.create({
          name: "Income",
          amount: req.body.amount,
          description: req.body.description,
          location: req.body.location,
          daymonth: newdate,
          hour: time,
          accountId: account.id,
          categoryId: category.id
        })
        res.status(200).send({
          message: "Success."
        })
      })
      .catch(err => {
        res.status(500).send({ message: err.message });
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  });  
}

exports.createExpense = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.name
      }
    })
    .then(account => {
      if (account.amount < req.body.amount) {
        return res.status(250).send({
          message: "Your account is not available!"
        })
      }

      Account.update({amount: account.amount -= req.body.amount}, {
        where: {
          id: account.id
        }
      })

      Category.findOne({
        where: {
          name: req.body.category_name
        }
      })
      .then(category => {
        var dateObj = new Date(Date.now());
        var month = dateObj.getMonth() + 1;
        var day = dateObj.getDate();
        var year = dateObj.getFullYear();

        var newdate = day + "/" + month + "/" + year;

        var hour = dateObj.getHours();
        var minute = dateObj.getMinutes();
        if (minute < 10) {
          minute = "0" + minute
        }

        var time = hour + ":" + minute;
        Transaction.create({
          name: "Expense",
          amount: req.body.amount,
          description: req.body.description,
          location: req.body.location,
          image: req.body.image,
          daymonth: newdate,
          hour: time,
          accountId: account.id,
          categoryId: category.id
        })

        sequelize.query(
          `UPDATE "budgets"
          SET "spent"= "spent" + ${req.body.amount}
          WHERE "budgets"."id" = (
            SELECT id
            FROM "budgets"
            WHERE extract(month from "createdAt") = ${month}
            AND "budgets"."userId" = ${user.id}
            AND "budgets"."categoryId" = ${category.id}
            AND "budgets"."name" = '${account.name}'
          );`
          ,
          {type: Sequelize.QueryTypes.UPDATE}
        )
          
        sequelize.query(
          `UPDATE "budgets" 
          SET "remain"= "total" - "spent"
          WHERE "budgets"."id" = (
            SELECT id
            FROM "budgets"
            WHERE extract(month from "createdAt") = ${month}
            AND "budgets"."userId" = ${user.id}
            AND "budgets"."categoryId" = ${category.id}
            AND "budgets"."name" = '${account.name}'
          );`
          ,
          {type: Sequelize.QueryTypes.UPDATE}
        )

        res.status(200).send({
           message: "Success."
        })
      })
      .catch(err => {
        res.status(500).send({ message: err.message });
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }); 
}

exports.createBudget = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Category.findOne({
      where: {
        name: req.body.budgetName
      }
    })
    .then(category => {
      Budget.create({
        name: req.body.accountName,
        total: req.body.budgetAmount,
        categoryId: category.id,
        userId: user.id,
        spent: 0,
        remain: req.body.budgetAmount
      })

      res.status(200).send({
        message: "Success."
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  });  
}

exports.getBudget = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.accountName
      }
    })
    .then(account => {
      if(!account) {
        return res.status(400).send({ message: "No account found" });
      }

      sequelize.query(
        `SELECT "categories"."name", "budgets"."total" as Amount_Total, "budgets"."spent" as Amount_Spent, "budgets"."remain" as Amount_Remain
        FROM "budgets" JOIN "categories" ON "budgets"."categoryId" =  "categories"."id"
        WHERE "budgets"."userId" = ${user.id}
          AND "budgets"."name" = '${account.name}'
          AND extract(month from "budgets"."createdAt") = ${req.body.month};`,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(data => {
        if (!account) {
          return res.status(400).send({
            message: "No"
          })
        }
        res.status(200).json(data)
      })
      .catch(err => {
        res.status(500).send({ message: err.message });
      }) 
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getNotExistCategory = (req, res)  => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    var dateObj = new Date(Date.now())
    var month = dateObj.getMonth() + 1

    sequelize.query(
      `SELECT "name" 
      FROM "categories" 
      WHERE "description" = 'Expense' AND  "categories"."id" NOT IN (
        SELECT "budgets"."categoryId" 
        FROM "budgets" JOIN "users" ON "budgets"."userId" = "users"."id"
        WHERE "budgets"."userId" = ${user.id}
          AND extract(month from "budgets"."createdAt") = ${month}
          AND "budgets"."name" = '${req.body.accountName}'
      ORDER BY "categories" ."name" ASC);`,
      {type: Sequelize.QueryTypes.SELECT}
    )
    .then(data => {
      if (!data) {
        return res.status(400)
      }
      return res.status(200).json(data)
    })
    .catch(err => {
      res.status(500).send({ message: err.message });
    }) 
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.createAccount = (req, res)  => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Bank.findOne({
      where: {
        name: req.body.name
      }
    })
    .then(bank => {
      Account.create({
        name: bank.name,
        amount: req.body.amount,
        userId: user.id,
        bankId: bank.id
      })
      .then(account => {
        res.status(200).send({
          message: "Your new account created"
        })
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }); 
}

exports.getAccount = (req, res)  => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findAll({
      where: {
        userId: user.id
      },
      attributes: {
        exclude: ['id', 'userId', 'bankId', 'createdAt', 'updatedAt']
      },
      order: [["name", "ASC"]]
    })
    .then(account => {
      if (!account) {
        return res.status(400).send({
          message: "No"
        })
      }
      
      res.status(200).json(account)
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getNotExistBank = (req, res)  => {
  User.findOne({
    where: {
      username: req.body.username
    },
    attributes: ['id']
  })
  .then(user => {
    var user_id = user.id
    sequelize.query(
      `SELECT "name" 
      FROM "banks" 
      WHERE "banks"."id" NOT IN (
        SELECT "bankId" 
        FROM "accounts" JOIN "users" ON "accounts"."userId" = "users"."id"
        WHERE "accounts"."userId" = ${user_id})
      ORDER BY "name" ASC;`,
      {type: Sequelize.QueryTypes.SELECT}
    )
    .then(data => {
      if (!data) {
        return res.status(400)
      }
      return res.status(200).json(data)

    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getTransaction = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.accountName
      }
    })
    .then(account => {
      sequelize.query(
        `SELECT "transactions"."id", "transactions"."name",  "transactions"."amount", "transactions"."hour", "transactions"."daymonth", "categories"."name" as "categoryName", "transactions"."description"
        FROM "transactions" JOIN "categories" ON "transactions"."categoryId" = "categories"."id" 
        WHERE "transactions"."accountId" = ${account.id}
        ORDER BY "transactions"."createdAt" DESC;`,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(transaction => {
        res.status(200).json(transaction)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.get_3Transaction = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.accountName
      }
    })
    .then(account => {
      sequelize.query(
        `SELECT "transactions"."name",  "transactions"."amount", "transactions"."hour", "transactions"."daymonth", "categories"."name" as "categoryName", "transactions"."description"
        FROM "transactions" JOIN "categories" ON "transactions"."categoryId" = "categories"."id" 
        WHERE "transactions"."accountId" = ${account.id}
        ORDER BY "transactions"."createdAt" DESC
        LIMIT 3;`,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(transaction => {
        res.status(200).json(transaction)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getIncomeTransaction = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.accountName
      }
    })
    .then(account => {
      sequelize.query(
        `SELECT "transactions"."name",  sum("transactions"."amount") as amount, "categories"."name" as "categoryName"
        FROM "transactions" JOIN "categories" ON "transactions"."categoryId" = "categories"."id" 
        WHERE "transactions"."accountId" = ${account.id} AND "transactions"."name" = 'Income'
        GROUP BY "transactions"."name","categoryName";`,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(transaction => {
        res.status(200).send(transaction)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  })
} 

exports.getExpenseTransaction = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.accountName
      }
    })
    .then(account => {
      sequelize.query(
        `SELECT "transactions"."name",  sum("transactions"."amount") as amount, "categories"."name" as "categoryName"
        FROM "transactions" JOIN "categories" ON "transactions"."categoryId" = "categories"."id" 
        WHERE "transactions"."accountId" = ${account.id} AND "transactions"."name" = 'Expense'
        GROUP BY "transactions"."name","categoryName";`,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(transaction => {
        res.status(200).json(transaction)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  })
} 

exports.getBalance = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.name
      },
      attributes: {
        exclude: ['id', 'userId', 'bankId', 'createdAt', 'updatedAt']
      }
    })
    .then(account => {
      res.status(200).json(account)
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.createTransfer = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.fromBank
      }
    })
    .then(accountFrom => {
      Account.findOne({
        where: {
          userId: user.id,
          name: req.body.toBank
        }
      })
      .then(accountTo => {
        if (accountFrom.amount < req.body.money) {
          return res.status(250).send({ message: "Your account is not available!"})
        }
        
        Account.update({amount: accountFrom.amount -= req.body.money}, {
          where: {
            id: accountFrom.id
          }
        })

        Account.update({amount: accountTo.amount += req.body.money}, {
          where: {
            id: accountTo.id
          }
        })

        var dateObj = new Date();
        var month = dateObj.getMonth();
        var day = dateObj.getDate();
        var year = dateObj.getFullYear();

        var newdate = day + "/" + month + "/" + year;

        var hour = dateObj.getHours();
        var minute = dateObj.getMinutes();

        var time = hour + ":" + minute;

        Transfer.create({
          amount: req.body.money,
          from_account_id: accountFrom.id,
          to_account_id: accountTo.id,
          description: req.body.description,
          daymonth: newdate,
          hour: time
        })
        .then(transfer => {
          return res.status(200).send({message: "Success"})
        })
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getIncome = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        name: req.body.name_bank,
        userId: user.id,
      }
    })
    .then(account => {
      Transaction.sum('amount',{
        where: {
          accountId: account.id,
          name: "Income"
        }
      })
      .then(data => {
        var item = `{"total": ${data}}`
        var obj = JSON.parse(item)
        res.status(200).json(obj)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getExpense = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        name: req.body.name_bank,
        userId: user.id,
      }
    })
    .then(account => {
      Transaction.sum('amount',{
        where: {
          accountId: account.id,
          name: "Expense"
        }
      })
      .then(data => {
        var item = `{"total": ${data}}`
        var obj = JSON.parse(item)
        res.status(200).json(obj)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  }) 
}

exports.getDetail = (req, res) => {
  sequelize.query(
    `SELECT "transactions"."amount", "categories"."name" as "categoryName", "transactions"."description", "transactions"."location", "transactions"."daymonth", "transactions"."hour"
        FROM "transactions" JOIN "categories" ON "transactions"."categoryId" = "categories"."id" 
        WHERE "transactions"."id" = ${req.body.transactionID}
        LIMIT 1;`,
        {type: Sequelize.QueryTypes.SELECT}
  )
  .then(data => {
    res.status(200).json(data)
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  })
}

exports.getFilter = (req, res) => {
  var param = req.query
  User.findOne({
    where: {
      username: param.user
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: param.account
      }
    })
    .then(account => {
      var sql =  
      `SELECT "transactions"."id", "transactions"."name",  "transactions"."amount", "transactions"."hour", "transactions"."daymonth", "categories"."name" as "categoryName", "transactions"."description"
      FROM "transactions" JOIN "categories" ON "transactions"."categoryId" = "categories"."id"
      WHERE "transactions"."accountId" = ${account.id} `

      if(param.filter != null && param.filter != "") {
        var temp = titleCase(param.filter)
        
        sql += `AND "transactions"."name" = '${temp}' `
      }

      if (param.sortby != null && param.filter != "") {
        if (param.sortby == "Lowest") {
          sql += `ORDER BY "transactions"."amount" `
        }
        
        else if (param.sortby == "Highest") {
          sql += `ORDER BY "transactions"."amount" DESC `
        }

        else if (param.sortby == "Oldest") {
          sql += `ORDER BY "transactions"."createdAt" `
        }

        else if (param.sortby == "Newest") {
          sql += `ORDER BY "transactions"."createdAt" DESC `
        }
      }
      else {
        sql += `ORDER BY "transactions"."createdAt" DESC `
      }

      sql += `;`
      sequelize.query(
        sql,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(transaction => {
        res.status(200).json(transaction)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  })
}

exports.getFrequency = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    Account.findOne({
      where: {
        userId: user.id,
        name: req.body.accountName
      }
    })
    .then(account => {
      var dateObj = new Date(Date.now());
      var month = dateObj.getMonth() + 1;
      var year = dateObj.getFullYear();
      sequelize.query(
        `SELECT "transactions"."amount" as frequency, DATE("transactions"."createdAt") + 1 as day
        FROM "transactions"
        WHERE "transactions"."accountId" = ${account.id} AND "transactions"."name" = 'Expense' AND extract(month from "transactions"."createdAt") = ${month} AND extract(year from "transactions"."createdAt") = ${year};`,
        {type: Sequelize.QueryTypes.SELECT}
      )
      .then(data => {
        res.status(200).send(data)
      })
    })
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  })
}


function titleCase(string) {
  //1. Tách các từ, cụm từ trong chuỗi ban đầu
  let sentence = string.toLowerCase().split(" ", "&");
  //2. Tạo vòng lặp và viết hoa chữ cái đầu tiên của các từ, cụm từ trên
  for(var i = 0; i< sentence.length; i++){
     sentence[i] = sentence[i][0].toUpperCase() + sentence[i].slice(1);
  }
  //3. Nối các từ, cụm từ đã xử lý ở trên và trả về kết quả

  if(sentence) {
    return string[0].toUpperCase() +   string.slice(1);
  }
  return sentence.join(" ");
}