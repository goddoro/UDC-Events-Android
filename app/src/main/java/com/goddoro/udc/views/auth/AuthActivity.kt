package com.goddoro.udc.views.auth

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.*
import com.goddoro.common.data.api.UnWrappingDataException
import com.goddoro.common.util.Navigator
import com.goddoro.common.util.ToastUtil
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivityAuthBinding
import dagger.android.AndroidInjection.inject
import dagger.android.support.DaggerAppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private val TAG = AuthActivity::class.java.simpleName

    private lateinit var mBinding : ActivityAuthBinding
    private val mViewModel : AuthViewModel by viewModel()

    private val navigator : Navigator by inject()

    private val mFragment1 = LoginFragment.newInstance()
    private val mFragment2 = SignUpFragment.newInstance()
    private val mFragment3 = EmailFindFragment.newInstance()
    private val mFragment4 = PasswordFindFragment.newInstance()
    private var mActiveFragment: Fragment = mFragment1


    private val toastUtil : ToastUtil by inject()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAuthBinding.inflate(LayoutInflater.from(this))

        debugE(TAG, mViewModel)
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        if ( savedInstanceState == null)  {
            initFragments()
        }

        observeViewModel()
    }



    private fun initFragments() {

        val fm = supportFragmentManager

        fm.beginTransaction().add(R.id.mContainer, mFragment1, "1").commit()
    }


    private fun changeFragment(position: Int) {

        val willShow = when (position) {
            1 -> mFragment1
            2 -> mFragment2
            3 -> mFragment3
            4 -> mFragment4
            else -> throw IllegalArgumentException()
        }
        supportFragmentManager.beginTransaction()



        supportFragmentManager.beginTransaction()
            .add(R.id.mContainer, willShow,null)
            .addToBackStack(null)
            .show(willShow)
            .commit()
        mActiveFragment = willShow
    }

    private fun observeViewModel () {


        mViewModel.apply {

            clickSignUpPage.observeOnce(this@AuthActivity){
                changeFragment(2)
            }

            clickFindEmailPage.observeOnce(this@AuthActivity){
                changeFragment(3)
            }

            clickFindPasswordPage.observeOnce(this@AuthActivity){
                changeFragment(4)
            }


            loginCompleted.observeOnce(this@AuthActivity){
                debugE(TAG, "Login Completed")
                navigator.startMainActivity(this@AuthActivity,true)
                finish()

            }

            errorInvoked.observeOnce(this@AuthActivity){
                debugE(TAG, "GOOODDD")
                if ( it is UnWrappingDataException) {
                    val errorMessage = when (it.errorCode) {
                        400 -> "존재하지 않는 유저입니다"
                        else -> it.message ?: ""
                    }

                    toastUtil.createToast(errorMessage)?.show()
                }
            }

        }
    }


}