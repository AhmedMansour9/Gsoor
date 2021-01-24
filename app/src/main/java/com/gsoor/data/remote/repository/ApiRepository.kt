package com.gsoor.data.remote.repository

import com.gsoor.data.remote.api.ApiService
import com.gsoor.data.remote.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.QueryMap
import javax.inject.Inject


class ApiRepository @Inject constructor(private val apiService: ApiService) : ApiService {

    override  fun userLogin(loginRequest: RequestLoginModel): Call<AccountResponse> =
        apiService.userLogin(loginRequest)

    override fun registerAdmin(loginRequest: RequestAdminRegisterModel): Call<AdminRegisterResponse> =
        apiService.registerAdmin(loginRequest)

    override fun roles(): Call<RolesResponse> = apiService.roles()
    override fun deleteroles(map: Map<String, String>): Call<DeleteRolesResponse> = apiService.deleteroles(map)
    override fun countries(): Call<CountriesResponse> = apiService.countries()

    override fun deletecountry(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletecountry(map)
    override fun createPermission(loginRequest: RequestCreatePermission): Call<AdminRegisterResponse> = apiService.createPermission(loginRequest)
    override fun editPermission(loginRequest: RequestCreatePermission): Call<AdminRegisterResponse> = apiService.editPermission(loginRequest)

    override fun createCountry(loginRequest: RequestAddCountry): Call<AddCountryResponse> = apiService.createCountry(loginRequest)

    override fun editCountry(loginRequest: RequestAddCountry): Call<AddCountryResponse> = apiService.editCountry(loginRequest)


    override fun cities(map: Map<String, String>): Call<CitiesResponse> = apiService.cities(map)

    override fun deletecity(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletecity(map)

    override fun createCity(loginRequest: RequestAddCity): Call<AddCountryResponse> = apiService.createCity(loginRequest)

    override fun editCity(loginRequest: RequestAddCity): Call<AddCountryResponse> = apiService.editCity(loginRequest)

    override fun mangers(): Call<AllMangersResponse> = apiService.mangers()

    override fun editManger(loginRequest: RequestEditManger): Call<DeleteCountryResponse> = apiService.editManger(loginRequest)

    override fun deletemanger(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletemanger(map)



    override fun categories(): Call<CategoriesResponse> = apiService.categories()

    override fun deletecategory(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletecategory(map)

    override fun createCategory(loginRequest: RequestSaveCategory): Call<AddCategoryResponse> = apiService.createCategory(loginRequest)

    override fun editCategory(loginRequest: RequestSaveCategory): Call<AddCategoryResponse> = apiService.editCategory(loginRequest)



    override fun subcategories(map: Map<String, String>): Call<SubCategoriesResponse> = apiService.subcategories(map)

    override fun deletesubcategory(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletesubcategory(map)

    override fun saveSubCategory(loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse> = apiService.saveSubCategory(loginRequest)

    override fun editSubCategory(loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse> = apiService.editSubCategory(loginRequest)
    override fun getMembersAccounts(map: Map<String, String>): Call<MembersAccountResponse> = apiService.getMembersAccounts(map)

    override fun editMemberStatus(
        language: String,
        @Body request :RequestUpdateStatus
    ): Call<UpDateStatusResponse> = apiService.editMemberStatus(language,request)

    override fun sendEmail(@QueryMap map: Map<String,String>): Call<UpDateStatusResponse> = apiService.sendEmail(map)



    override fun getPackadges(): Call<PackagesResponse>  = apiService.getPackadges()

    override fun deletePackadge(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletePackadge(map)

    override fun createPackadge(loginRequest: RequestSavePackadges): Call<AddPackadgesResponse> = apiService.createPackadge(loginRequest)
    override fun editPackadges(loginRequest: RequestSavePackadges): Call<AddPackadgesResponse>  = apiService.editPackadges(loginRequest)


    override fun packagedetails(map: Map<String, String>): Call<PackageDetailsResponse> = apiService.packagedetails(map)
    override fun deletepackagedetails(map: Map<String, String>): Call<DeleteCountryResponse> = apiService.deletepackagedetails(map)
    override fun createPackageDetails(loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse> = apiService.createPackageDetails(loginRequest)
    override fun editPackageDetails(loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse> = apiService.editPackageDetails(loginRequest)
    override fun getPolicyStatus(
        language: String,
        map: Map<String, String>
    ): Call<PolicyResponse> = apiService.getPolicyStatus(language,map)

    override fun savePolicy(language: String, map: Map<String, String>): Call<SavePolicyResponse> = apiService.savePolicy(language,map)

}