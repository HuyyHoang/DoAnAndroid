package com.example.doancuoiky.API;

import com.example.doancuoiky.ResetPass.ChangePassRequest;
import com.example.doancuoiky.ResetPass.ChangePassResponse;
import com.example.doancuoiky.Home.SpendRequest;
import com.example.doancuoiky.Home.SpendResponse;
import com.example.doancuoiky.Login.UserRequest;
import com.example.doancuoiky.Login.UserResponse;
import com.example.doancuoiky.OTP.CreateOTPRequest;
import com.example.doancuoiky.OTP.CreateOTPResponse;
import com.example.doancuoiky.account.CreateRequest;
import com.example.doancuoiky.account.CreateResponse;
import com.example.doancuoiky.account.GetBalanceRequest;
import com.example.doancuoiky.account.GetBalanceResponse;
import com.example.doancuoiky.account.GetIncomeExpenseRequest;
import com.example.doancuoiky.account.GetIncomeExpenseResponse;
import com.example.doancuoiky.account.ItemAccountRequest;
import com.example.doancuoiky.account.ItemAccountResponse;
import com.example.doancuoiky.budget.BudgetRequest;
import com.example.doancuoiky.budget.BudgetResponse;
import com.example.doancuoiky.budget.CreateBudgetRequest;
import com.example.doancuoiky.budget.CreateBudgetResponse;
import com.example.doancuoiky.budget.GetCategoryRequest;
import com.example.doancuoiky.budget.GetCategoryResponse;
import com.example.doancuoiky.forgotpassword.ForgotRequest;
import com.example.doancuoiky.forgotpassword.ForgotResponse;
import com.example.doancuoiky.transaction.ChartReponse;
import com.example.doancuoiky.transaction.ChartRequest;
import com.example.doancuoiky.transaction.CreateIncomeRequest;
import com.example.doancuoiky.transaction.CreateIncomeResponse;
import com.example.doancuoiky.transaction.ItemRequest;
import com.example.doancuoiky.transaction.ItemResponse;
import com.example.doancuoiky.transfer.TransferRequest;
import com.example.doancuoiky.transfer.TransferResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UserService {

   @POST("/api/auth/signup")
   Call<UserResponse> saveUser(@Body UserRequest userRequest);

   @POST("/api/user")
   Call<UserResponse> getVerify(@Header("x-access-token") String accessToken);

   @POST("/api/auth/signin")
   Call<UserResponse> loginCall (@Body UserRequest userRequest);

   @POST("/api/auth/checkpassword")
   Call<ChangePassResponse> verifyCall (@Body ChangePassRequest changePassRequest);

   @POST("/api/auth/change")
   Call<ChangePassResponse> changeCall (@Body ChangePassRequest changePassRequest);

   @POST("/api/auth/create/otp")
   Call<CreateOTPResponse> createOTPCall (@Body CreateOTPRequest createRequest);

   @POST("/api/auth/checkotp")
   Call<ForgotResponse> resetCall (@Body ForgotRequest forgotRequest);

   @POST("/api/post/list/transaction")
   Call<List<ItemResponse>> getAllItem(@Body ItemRequest itemRequest);

   @POST("/api/post/list/transaction/3")
   Call<List<ItemResponse>> get_3Item(@Body ItemRequest itemRequest);

   @POST("/api/post/list/account")
   Call<List<ItemAccountResponse>> getlistAccount(@Body ItemAccountRequest itemAccountRequest);

   @POST("/api/post/list/not/bank")
   Call<List<ItemAccountResponse>> getlistNotExistAccount(@Body ItemAccountRequest itemAccountRequest);

   @POST("/api/post/create/account")
   Call<CreateResponse> create_accountCall(@Body CreateRequest createRequest);

   @POST("/api/post/balance")
   Call<GetBalanceResponse> getBalance(@Body GetBalanceRequest getBalanceRequest);

   @POST("/api/post/create/income")
   Call<CreateIncomeResponse> create_incomeCall(@Body CreateIncomeRequest createIncomeRequest);

   @POST("/api/post/create/expense")
   Call<CreateIncomeResponse> create_expenseCall(@Body CreateIncomeRequest createIncomeRequest);

   @POST("/api/post/create/transfer")
   Call<TransferResponse> create_transferCall(@Body TransferRequest transferRequest);

   @POST("/api/post/list/transaction/income")
   Call<List<ChartReponse>> getChartResponse(@Body ChartRequest chartRequest);

   @POST("/api/post/list/transaction/expense")
   Call<List<ChartReponse>> getChartResponseExpense(@Body ChartRequest chartRequest);

   @POST("/api/post/income")
   Call<GetIncomeExpenseResponse> getIncomeCall(@Body GetIncomeExpenseRequest getIncomeExpenseRequest);

   @POST("/api/post/expense")
   Call<GetIncomeExpenseResponse> getExpenseCall(@Body GetIncomeExpenseRequest getIncomeExpenseRequest);

   @POST("/api/post/frequency")
   Call<List<SpendResponse>> getSpendResponse (@Body SpendRequest spendRequest);


   @POST("/api/post/list/budget")
   Call<List<BudgetResponse>> getBudgetResponse (@Body BudgetRequest budgetRequest);

   @POST("/api/post/create/budget")
   Call<CreateBudgetResponse> createBudget (@Body CreateBudgetRequest createBudgetRequest);

   @POST("/api/post/list/not/category")
   Call<List<GetCategoryResponse>> getlistNotExistCategory(@Body GetCategoryRequest getCategoryRequest);

   @POST("/api/post/filter")
   Call<List<ItemResponse>> getAllTracsaction(@Query(value = "user", encoded = true) String username,
                                              @Query(value = "account", encoded = true) String accountName,
                                              @Query(value = "filter", encoded = false) String filterBy,
                                              @Query(value = "sortby", encoded = true) String sortBy);

}
