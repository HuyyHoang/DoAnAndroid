const { authJwt } = require("../middleware");
const controller = require("../controllers/user.controller");

module.exports = function(app) {
  app.use(function(req, res, next) {
    res.header(
      "Access-Control-Allow-Headers",
      "x-access-token, Origin, Content-Type, Accept"
    );
    next();
  });

  app.post(
    "/api/user",
    [authJwt.verifyToken],
    controller.userBoard
  );

  app.post(
    "/api/post/create/income",
    controller.createIncome
  );

  app.post(
    "/api/post/create/expense",
    controller.createExpense
  );

  app.post(
    "/api/post/create/budget",
    controller.createBudget
  );

  app.post(
    "/api/post/list/budget",
    controller.getBudget
  );

  app.post(
    "/api/post/list/not/category",
    controller.getNotExistCategory
  )

  app.post(
    "/api/post/create/account",
    controller.createAccount
  )

  app.post(
    "/api/post/create/transfer",
    controller.createTransfer
  )

  app.post(
    "/api/post/list/account",
    controller.getAccount
  )

  app.post(
    "/api/post/list/not/bank",
    controller.getNotExistBank
  )

  app.post(
    "/api/post/list/transaction",
    controller.getTransaction
  )

  app.post(
    "/api/post/list/transaction/3",
    controller.get_3Transaction
  )

  app.post(
    "/api/post/list/transaction/income",
    controller.getIncomeTransaction
  )

  app.post(
    "/api/post/list/transaction/expense",
    controller.getExpenseTransaction
  )

  app.post(
    "/api/post/list/expense",
    controller.getListExpense
  );

  app.post(
    "/api/post/list/income",
    controller.getListIncome
  );
  
  app.post(
    "/api/post/balance",
    controller.getBalance
  )

  app.post(
    "/api/post/income",
    controller.getIncome
  )

  app.post(
    "/api/post/expense",
    controller.getExpense
  )

  app.post(
    "/api/post/detail",
    controller.getDetail
  )

  app.post(
    "/api/post/filter",
    controller.getFilter
  )

  app.post(
    "/api/post/frequency",
    controller.getFrequency
  )
};
