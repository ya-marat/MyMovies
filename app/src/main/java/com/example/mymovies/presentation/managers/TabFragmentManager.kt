package com.example.mymovies.presentation.managers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mymovies.empty
import java.lang.RuntimeException
import java.util.Stack

class TabFragmentManager(
    private val fragmentManager: FragmentManager,
    private val mainContainerId: Int
) {

    data class TabState(
        val stack: Stack<String> = Stack(),
        var currentFragmentTag: String = String.empty()
    )

    data class TabStory(
        val tabId: Int
    )

    private val tabs = mutableMapOf<Int, TabState>()
    private val tabStoryStack = Stack<TabStory>()
    private var currentTabId = TAB_DEFAULT_VALUE;

    var onTabChanged: ((tabId: Int) -> Unit)? = null

    fun registerTab(tabId: Int, rootFragment: Fragment) {

        if (tabs.contains(tabId)) {
            return
        }

        tabs[tabId] = TabState()
        val tag = addFragmentToTab(tabId, rootFragment)
        tabs[tabId]?.stack?.push(tag)
    }

    fun navigateToFragment(fragment: Fragment) {
        addFragmentToTab(currentTabId, fragment)
    }

    fun navigateToTab(tabId: Int) {

        if (tabId != currentTabId && currentTabId != TAB_DEFAULT_VALUE) {
            val existTabStory = tabStoryStack.find { tabStory -> tabStory.tabId == tabId }
            if (existTabStory != null) {
                tabStoryStack.remove(existTabStory)
                tabStoryStack.push(existTabStory)
            } else {
                tabStoryStack.push(TabStory(tabId))
            }
        }

        if (currentTabId != TAB_DEFAULT_VALUE)
            hideTab(currentTabId)

        showTab(tabId)
        currentTabId = tabId
    }

    fun onBackPressed(): Boolean {
        val isStackNotEmpty = if (tabStoryStack.size > 0) {
            val popTabStory = tabStoryStack.peek()
            val tab = getTab(popTabStory.tabId)
            if (tab.stack.size > 1) {
                fragmentManager.popBackStack()
                tab.stack.pop()

                if(tab.stack.size == 1) {
                    tabStoryStack.pop()
                }



            } else {
                throw RuntimeException("Tab with id ${popTabStory.tabId} has zero fragments in stack")
            }
            true
        } else {
            false
        }

        return isStackNotEmpty
    }

    private fun addFragmentToTab(tabId: Int, fragment: Fragment): String {
        val tag = generateFragmentTag(fragment)

        val tab = tabs[tabId]

        fragmentManager.beginTransaction().apply {
            add(mainContainerId, fragment, tag)

            tab?.let { t ->
                if (t.stack.size > 1) {
                    addToBackStack(tag)
                }

                t.stack.push(tag)
                t.currentFragmentTag = tag
            }

            commit()
        }

        return tag
    }

    private fun showTab(tabId: Int): TabState {
        val tab = tabs[tabId] ?: throw RuntimeException("Tab with id $tabId does not exist")
        val tabCurrentFragmentTag = tab.stack.peek()
        showFragmentByTag(tabCurrentFragmentTag)
        onTabChanged?.invoke(tabId)
        return tab
    }

    private fun hideTab(tabId: Int): TabState {
        val tab = tabs[tabId] ?: throw RuntimeException("Tab with id $tabId doesn't exist")
        val tabCurrentFragmentTag = tab.stack.peek()
        hideFragmentByTag(tabCurrentFragmentTag)
        return tab
    }

    private fun showFragmentByTag(tag: String) {
        fragmentManager.findFragmentByTag(tag).let { fragment ->
            fragment?.let {
                fragmentManager.beginTransaction().show(fragment).commit()
            }
        }
    }

    private fun hideFragmentByTag(tag: String) {
        fragmentManager.findFragmentByTag(tag).let { fragment ->
            fragment?.let {
                fragmentManager.beginTransaction().hide(it).commit()
            }
        }
    }

    private fun getTab(tabId: Int): TabState {
        return tabs[tabId] ?: throw RuntimeException("Tab with id $tabId doesn't exist")
    }

    private fun generateFragmentTag(fragment: Fragment): String {
        return "${fragment::class.simpleName}_${System.currentTimeMillis()}"
    }

    companion object {
        const val TAB_DEFAULT_VALUE = -1
    }
}