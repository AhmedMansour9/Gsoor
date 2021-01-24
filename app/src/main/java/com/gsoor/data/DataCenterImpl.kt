package com.gsoor.data

import com.gsoor.data.remote.model.*
import com.gsoor.data.remote.repository.ApiRepository
import retrofit2.Call
import javax.inject.Inject

class DataCenterImpl @Inject constructor(
    private val apiRepository: ApiRepository
) :
    DataCenterManager {

    override  fun loginAccount(loginRequest: RequestLoginModel): Call<AccountResponse> =
        apiRepository.userLogin(loginRequest)

    override fun registerAdmin(loginRequest: RequestAdminRegisterModel): Call<AdminRegisterResponse> =
        apiRepository.registerAdmin(loginRequest)

    override fun roles(): Call<RolesResponse> = apiRepository.roles()


    override fun deleteRoles(map: Map<String, String>): Call<DeleteRolesResponse> = apiRepository.deleteroles(map)
    override fun countries(): Call<CountriesResponse> = apiRepository.countries()

    override fun deleteCountries(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletecountry(map)
    override fun createPerimssion(loginRequest: RequestCreatePermission): Call<AdminRegisterResponse> = apiRepository.createPermission(loginRequest)
    override fun editPerimssion(loginRequest: RequestCreatePermission): Call<AdminRegisterResponse> = apiRepository.editPermission(loginRequest)
    override fun createCountry(loginRequest: RequestAddCountry): Call<AddCountryResponse> = apiRepository.createCountry(loginRequest)

    override fun editCountry(loginRequest: RequestAddCountry): Call<AddCountryResponse> = apiRepository.editCountry(loginRequest)

    override fun cities(map: Map<String, String>): Call<CitiesResponse> = apiRepository.cities(map)

    override fun deletecity(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletecity(map)

    override fun createCity(loginRequest: RequestAddCity): Call<AddCountryResponse> = apiRepository.createCity(loginRequest)

    override fun editCity(loginRequest: RequestAddCity): Call<AddCountryResponse> = apiRepository.editCity(loginRequest)
    override fun mangers(): Call<AllMangersResponse> = apiRepository.mangers()

    override fun editManger(loginRequest: RequestEditManger): Call<DeleteCountryResponse> = apiRepository.editManger(loginRequest)

    override fun deletemanger(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletemanger(map)


    override fun categories(): Call<CategoriesResponse> = apiRepository.categories()

    override fun deletecategory(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletecategory(map)

    override fun createCategory(loginRequest: RequestSaveCategory): Call<AddCategoryResponse> = apiRepository.createCategory(loginRequest)

    override fun editCategory(loginRequest: RequestSaveCategory): Call<AddCategoryResponse> = apiRepository.editCategory(loginRequest)


    override fun subcategories(map: Map<String, String>): Call<SubCategoriesResponse> = apiRepository.subcategories(map)

    override fun deletesubcategory(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletesubcategory(map)

    override fun saveSubCategory(loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse>  = apiRepository.saveSubCategory(loginRequest)

    override fun editSubCategory(loginRequest: RequestSaveSubCategory): Call<AddSubCategoryResponse> = apiRepository.editSubCategory(loginRequest)


    override fun getMembersAccounts(map: Map<String, String>): Call<MembersAccountResponse> =apiRepository.getMembersAccounts(map)

    override fun editMemberStatus(
        language: String,
       request :RequestUpdateStatus
    ): Call<UpDateStatusResponse> = apiRepository.editMemberStatus(language,request)

    override fun sendEmail(map: Map<String, String>): Call<UpDateStatusResponse> = apiRepository.sendEmail(map)

    override fun getPackadges(): Call<PackagesResponse> = apiRepository.getPackadges()

    override fun deletePackadge(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletePackadge(map)

    override fun createPackadge(loginRequest: RequestSavePackadges): Call<AddPackadgesResponse> = apiRepository.createPackadge(loginRequest)

    override fun editPackadges(loginRequest: RequestSavePackadges): Call<AddPackadgesResponse> = apiRepository.editPackadges(loginRequest)


    override fun packagedetails(map: Map<String, String>): Call<PackageDetailsResponse> = apiRepository.packagedetails(map)

    override fun deletepackagedetails(map: Map<String, String>): Call<DeleteCountryResponse> = apiRepository.deletepackagedetails(map)

    override fun createPackageDetails(loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse>  = apiRepository.createPackageDetails(loginRequest)

    override fun editPackageDetails(loginRequest: RequestSavePackageDetails): Call<AddPackageDetailsResponse> = apiRepository.editPackageDetails(loginRequest)
    override fun getPolicyStatus(
        language: String,
        map: Map<String, String>
    ): Call<PolicyResponse> = apiRepository.getPolicyStatus(language,map)

    override fun savePolicy(language: String, map: Map<String, String>): Call<SavePolicyResponse> = apiRepository.savePolicy(language,map)

}