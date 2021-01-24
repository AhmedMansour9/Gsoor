package com.gsoor.data

import com.gsoor.data.remote.model.*
import retrofit2.Call
import retrofit2.http.QueryMap

interface DataCenterManager {

    fun loginAccount(loginRequest: RequestLoginModel): Call<AccountResponse>

    fun registerAdmin(loginRequest: RequestAdminRegisterModel): Call<AdminRegisterResponse>

    fun roles(): Call<RolesResponse>

    fun deleteRoles( map: Map<String,String>): Call<DeleteRolesResponse>

    fun countries(): Call<CountriesResponse>

    fun deleteCountries( map: Map<String,String>): Call<DeleteCountryResponse>

    fun createPerimssion(loginRequest: RequestCreatePermission): Call<AdminRegisterResponse>
    fun editPerimssion(loginRequest: RequestCreatePermission): Call<AdminRegisterResponse>

     fun createCountry(loginRequest: RequestAddCountry): Call<AddCountryResponse>

     fun editCountry(loginRequest: RequestAddCountry): Call<AddCountryResponse>


     fun cities(map: Map<String, String>): Call<CitiesResponse>

     fun deletecity(map: Map<String, String>): Call<DeleteCountryResponse>

     fun createCity(loginRequest: RequestAddCity): Call<AddCountryResponse>

     fun editCity(loginRequest: RequestAddCity): Call<AddCountryResponse>

     fun mangers(): Call<AllMangersResponse>

     fun editManger(loginRequest: RequestEditManger): Call<DeleteCountryResponse>

     fun deletemanger(map: Map<String, String>): Call<DeleteCountryResponse>


     fun categories(): Call<CategoriesResponse>

     fun deletecategory(map: Map<String, String>): Call<DeleteCountryResponse>

     fun createCategory(loginRequest: RequestSaveCategory): Call<AddCategoryResponse>

     fun editCategory(loginRequest: RequestSaveCategory): Call<AddCategoryResponse>


     fun subcategories(map: Map<String, String>): Call<SubCategoriesResponse>

     fun deletesubcategory(map: Map<String, String>): Call<DeleteCountryResponse>

     fun saveSubCategory(loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse>

     fun editSubCategory(loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse>


     fun getMembersAccounts(map: Map<String, String>): Call<MembersAccountResponse>

     fun editMemberStatus(
        language: String,
        request :RequestUpdateStatus
    ): Call<UpDateStatusResponse>

     fun sendEmail(@QueryMap map: Map<String,String>): Call<UpDateStatusResponse>



     fun getPackadges(): Call<PackagesResponse>
     fun deletePackadge(map: Map<String, String>): Call<DeleteCountryResponse>
     fun createPackadge(loginRequest: RequestSavePackadges): Call<AddPackadgesResponse>
     fun editPackadges(loginRequest: RequestSavePackadges): Call<AddPackadgesResponse>


     fun packagedetails(map: Map<String, String>): Call<PackageDetailsResponse>
     fun deletepackagedetails(map: Map<String, String>): Call<DeleteCountryResponse>
     fun createPackageDetails(loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse>
     fun editPackageDetails(loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse>

     fun getPolicyStatus(
        language: String,
        map: Map<String, String>
    ): Call<PolicyResponse>

     fun savePolicy(language: String, map: Map<String, String>): Call<SavePolicyResponse>

}