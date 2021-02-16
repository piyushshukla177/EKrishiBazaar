package com.service.ekrishibazaar.util;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiHelper {

//    @FormUrlEncoded
//    @POST("getProduct")
//    Call<ApiResponse<List<ProductList>>> getProductList(@Field("code") String code, @Field("password") String password);

    @FormUrlEncoded
    @POST("offer/")
    Call<String> MakeOffer(@Field("postid") String postid, @Field("category") String category, @Field("price") String price, @Field("offered_price") String offered_price, @Field("phonenumber") String phonenumber, @Field("vid") String vid);

//    @Headers({"Content-Type: application/json"})
//    @POST("getInvoiceData")
//    Call<InvoiceInfo> getBillInfo(@Query("nmfj") JSONObject obj);

//    @Headers({"Content-Type: application/json"})
//    @POST("getInvoiceData")
//    Call<InvoiceInfo> getBillInfo(@Body JsonObject obj);
//
//    @Headers({"Content-Type: application/json"})
//    @POST("getReturnList")
//    Call<DT> getreturnList(@Body JsonObject obj);
}
