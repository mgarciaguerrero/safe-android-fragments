package io.github.mgarciaguerrero.safefragments.extensions

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Method to replace the fragment. The [fragment] is added to the container view with id
 * [containerViewId] and a [tag]. The operation is performed by the childFragmentManager.
 * @return the fragment replaced.
 */
fun <T : Fragment> Fragment.replaceFragmentSafely(
    fragment: T,
    tag: String,
    @IdRes containerViewId: Int,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes popEnterAnimation: Int = 0,
    @AnimRes popExitAnimation: Int = 0,
    commitNow: Boolean = false,
    addToBackStack: Boolean = false
): T {
    if (isAdded) {
        val ft = childFragmentManager.beginTransaction()
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        ft.replace(containerViewId, fragment, tag)
        activity?.let {
            if (addToBackStack) {
                ft.addToBackStack(tag)
            }
            if (childFragmentManager.isStateSaved) {
                if (commitNow) ft.commitNowAllowingStateLoss() else ft.commitAllowingStateLoss()
            } else {
                if (commitNow) ft.commitNow() else ft.commit()
            }
        }
    }
    return fragment
}

/**
 * Method to add the fragment. The [fragment] is added to the container view with id
 * [containerViewId] and a [tag]. The operation is performed by the childFragmentManager.
 * @return the fragment added.
 */
fun <T : Fragment> Fragment.addFragmentSafely(
    fragment: T,
    tag: String,
    @IdRes containerViewId: Int,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes popEnterAnimation: Int = 0,
    @AnimRes popExitAnimation: Int = 0,
    commitNow: Boolean = false,
    addToBackStack: Boolean = false
): T {
    return (findFragmentByTag(tag) as T?) ?: fragment.also {
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
            add(containerViewId, it, tag)
            if (addToBackStack) {
                addToBackStack(tag)
            }
            if (childFragmentManager.isStateSaved) {
                if (commitNow) commitNowAllowingStateLoss() else commitAllowingStateLoss()
            } else {
                if (commitNow) commitNow() else commit()
            }
        }
    }
}

/**
 * Method to get fragment by tag. The operation is performed by the childFragmentManager.
 */
fun Fragment.findFragmentByTag(tag: String): Fragment? {
    return childFragmentManager.findFragmentByTag(tag)
}