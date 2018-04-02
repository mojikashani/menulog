package com.moji.menulog.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.moji.menulog.R
import com.moji.menulog.presentation.fragments.base.BaseFragment
import com.moji.menulog.presentation.views.GetPostcodeView
import com.moji.menulog.presentation.presenters.PostcodePresenter
import com.moji.menulog.utils.EXTRA_POSTCODE
import com.moji.menulog.utils.MY_PERMISSIONS_REQUEST_LOCATION
import kotlinx.android.synthetic.main.fragment_postcode.*


class PostCodeFragment : BaseFragment<PostcodePresenter>() , GetPostcodeView {

    override fun instantiatePresenter(): PostcodePresenter {
        return PostcodePresenter( this)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_postcode, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        setViews()
    }

    private fun setViews(){
        setToolbarTitle(getString(R.string.app_name))

        btnFind.setOnClickListener {
            hideKeyboard()
            val postcode = editPostcode.text.toString()//example: se19
            if(postcode.isNotEmpty()) {
                navigateToMainFragment(postcode)
            }else{
                Toast.makeText(activity,getString(R.string.error_postcode_is_empty), Toast.LENGTH_LONG).show()
            }
        }

        btnMyLocation.setOnClickListener{
            when (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                PackageManager.PERMISSION_GRANTED ->{
                    // Permission has already been granted
                    getPostcodeFromLocation()
                }
                else ->{
                    // No need for explanation: it is obvious to user
                    ActivityCompat.requestPermissions(activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    getPostcodeFromLocation()
                } else {
                    // permission denied
                }
                return
            }
        }
    }

    override fun onPostcodeFetched(postcode: String?) {
        editPostcode.setText(postcode)
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    // this is called when 401 error is sent from server
    // in certain circumstances this should navigate user to login page
    override fun onAuthorizationError(e: Throwable) {
        Toast.makeText(activity,getString(R.string.error_authentication_failed), Toast.LENGTH_LONG).show()
    }

    // this is called when any error except for 401 and no network error happens
    // during the api call
    override fun onError(message: String?) {
        Toast.makeText(activity,message?:getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show()
    }

    // this is called when there is no network during the api call
    override fun onNoNetworkError() {
        Toast.makeText(activity,getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show()
    }

    override fun showLoading(message: String) {
        progressBar.visibility = View.VISIBLE
    }

    private fun getPostcodeFromLocation(){
        try {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            presenter.getPostcode(location.latitude, location.longitude)
                        }
                    }
        }catch (ex : SecurityException){
            ex.printStackTrace()
        }
    }

    private fun navigateToMainFragment(postcode : String){
        val fragment = MainFragment()
        val bundle = Bundle()
        bundle.putString(EXTRA_POSTCODE, postcode)
        fragment.arguments = bundle

        navigateTo(fragment)
    }
}
