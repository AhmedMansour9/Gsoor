package com.gsoor.data.remote.api

import com.gsoor.data.remote.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("Login")
     fun userLogin(@Body loginRequest: RequestLoginModel): Call<AccountResponse>

    @POST("RegisterAdmin")
    fun registerAdmin(@Body loginRequest: RequestAdminRegisterModel): Call<AdminRegisterResponse>

    @GET("Roles")
    fun roles(): Call<RolesResponse>

    @DELETE("Delete")
    fun deleteroles(@QueryMap map: Map<String,String>): Call<DeleteRolesResponse>

    @POST("CreatePermissions")
    fun createPermission(@Body loginRequest: RequestCreatePermission): Call<AdminRegisterResponse>

    @POST("UpdatePermissions")
    fun editPermission(@Body loginRequest: RequestCreatePermission): Call<AdminRegisterResponse>

    @GET("Country/GetPage")
    fun countries(): Call<CountriesResponse>

    @DELETE("Country/Delete")
    fun deletecountry(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>

    @POST("Country/SaveNew")
    fun createCountry(@Body loginRequest: RequestAddCountry): Call<AddCountryResponse>

    @PUT("Country/Update")
    fun editCountry(@Body loginRequest: RequestAddCountry): Call<AddCountryResponse>


    @GET("City/GetAllByCountry")
    fun cities(@QueryMap map: Map<String,String>): Call<CitiesResponse>

    @DELETE("City/Delete")
    fun deletecity(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>

    @POST("City/SaveNew")
    fun createCity(@Body loginRequest: RequestAddCity): Call<AddCountryResponse>

    @PUT("City/Update")
    fun editCity(@Body loginRequest: RequestAddCity): Call<AddCountryResponse>


    @GET("GetAllManger")
    fun mangers(): Call<AllMangersResponse>

    @PUT("UpdateManger")
    fun editManger(@Body loginRequest: RequestEditManger): Call<DeleteCountryResponse>

    @DELETE("DeleteManger")
    fun deletemanger(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>


    @GET("Category/GetPage")
    fun categories(): Call<CategoriesResponse>

    @DELETE("Category/Delete")
    fun deletecategory(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>

    @POST("Category/SaveNew")
    fun createCategory(@Body loginRequest: RequestSaveCategory): Call<AddCategoryResponse>

    @PUT("Category/Update")
    fun editCategory(@Body loginRequest: RequestSaveCategory): Call<AddCategoryResponse>



    @GET("SubCategory/GetAllByCategory")
    fun subcategories(@QueryMap map: Map<String,String>): Call<SubCategoriesResponse>

    @DELETE("SubCategory/Delete")
    fun deletesubcategory(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>

    @POST("SubCategory/SaveNew")
    fun saveSubCategory(@Body loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse>

    @PUT("SubCategory/Update")
    fun editSubCategory(@Body loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse>

    @GET("MembersAccount/GetMembersCertified")
    fun getMembersAccounts(@QueryMap map: Map<String,String>): Call<MembersAccountResponse>

    @POST("{link}" )
    fun editMemberStatus(@Path(
        value = "link",
        encoded = true
    ) language: String,@Body request :RequestUpdateStatus): Call<UpDateStatusResponse>

    @POST("MembersAccount/SendEmail")
    fun sendEmail(@QueryMap map: Map<String,String>): Call<UpDateStatusResponse>



    @GET("Packages/GetPage")
    fun getPackadges(): Call<PackagesResponse>

    @DELETE("Packages/Delete")
    fun deletePackadge(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>

    @POST("Packages/SaveNew")
    fun createPackadge(@Body loginRequest: RequestSavePackadges): Call<AddPackadgesResponse>

    @PUT("Packages/Update")
    fun editPackadges(@Body loginRequest: RequestSavePackadges): Call<AddPackadgesResponse>



    @GET("PackagesDetails/GetAllByPackage")
    fun packagedetails(@QueryMap map: Map<String,String>): Call<PackageDetailsResponse>

    @DELETE("PackagesDetails/Delete")
    fun deletepackagedetails(@QueryMap map: Map<String,String>): Call<DeleteCountryResponse>

    @POST("PackagesDetails/SaveNew")
    fun createPackageDetails(@Body loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse>

    @PUT("PackagesDetails/Update")
    fun editPackageDetails(@Body loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse>



    @GET("{link}"+"/GetPolicy")
    fun getPolicyStatus(@Path(
        value = "link",
        encoded = true
    ) language: String,@QueryMap map: Map<String,String>): Call<PolicyResponse>

    @POST("{link}"+"/SaveNew" )
    fun savePolicy(@Path(
        value = "link",
        encoded = true
    ) language: String,@QueryMap map: Map<String,String>): Call<SavePolicyResponse>

}