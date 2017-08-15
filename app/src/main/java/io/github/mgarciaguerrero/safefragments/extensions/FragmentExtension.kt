package io.github.mgarciaguerrero.safefragments.extensions

import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment

/**
 * Method to replace the fragment. The [fragment] is added to the container view with id
 * [containerViewId] and a [tag]. The operation is performed by the childFragmentManager.
 * This method checks if fragment is added.
 */
fun Fragment.replaceFragmentSafely(fragment: Fragment,
                                   tag: String,
                                   allowStateLoss: Boolean = false,
                                   @IdRes containerViewId: Int,
                                   @AnimRes enterAnimation: Int = 0,
                                   @AnimRes exitAnimation: Int = 0,
                                   @AnimRes popEnterAnimation: Int = 0,
                                   @AnimRes popExitAnimation: Int = 0) {
    if (isAdded) {
        val ft = childFragmentManager.beginTransaction()
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        ft.replace(containerViewId, fragment, tag)
        if (!childFragmentManager.isStateSaved) {
            ft.commit()
        } else if (allowStateLoss) {
            ft.commitAllowingStateLoss()
        }
    }
}

/**
 * Method to add the fragment. The [fragment] is added to the container view with id
 * [containerViewId] and a [tag]. The operation is performed by the childFragmentManager.
 * This method checks if fragment exists and it is added.
 * @return the fragment added.
 */
fun <T : Fragment> Fragment.addFragmentSafely(fragment: T,
                                              tag: String,
                                              allowStateLoss: Boolean = false,
                                              @IdRes containerViewId: Int,
                                              @AnimRes enterAnimation: Int = 0,
                                              @AnimRes exitAnimation: Int = 0,
                                              @AnimRes popEnterAnimation: Int = 0,
                                              @AnimRes popExitAnimation: Int = 0): T {
    if (isAdded && !existsFragmentByTag(tag)) {
        val ft = childFragmentManager.beginTransaction()
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        ft.add(containerViewId, fragment, tag)
        if (!childFragmentManager.isStateSaved) {
            ft.commit()
        } else if (allowStateLoss) {
            ft.commitAllowingStateLoss()
        }
        return fragment
    }
    return findFragmentByTag(tag) as T
}

/**
 * Method to check if fragment exists. The operation is performed by the childFragmentManager.
 */
fun Fragment.existsFragmentByTag(tag: String): Boolean {
    return childFragmentManager.findFragmentByTag(tag) != null
}

/**
 * Method to get fragment by tag. The operation is performed by the childFragmentManager.
 */
fun Fragment.findFragmentByTag(tag: String): Fragment? {
    return childFragmentManager.findFragmentByTag(tag)
}