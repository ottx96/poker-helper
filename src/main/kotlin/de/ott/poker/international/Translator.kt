package de.ott.poker.international

import tornadofx.get
import java.net.URLClassLoader
import java.util.*

class Translator private constructor() {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            loadResourceBundle(DEFAULT_LOCALE).apply {
                keys.iterator().forEach {
                    println(this[it])
                }
            }
        }

        private val RESOURCE_BUNDLE_NAME = "de.ott.poker.translate/"
        private val DEFAULT_LOCALE = Locale.ENGLISH

        private var locale: Locale = Locale.getDefault(Locale.Category.DISPLAY)

        private val defaultLanguage: ResourceBundle
        private var chosenLanguage: ResourceBundle

        init {
            defaultLanguage = loadResourceBundle(DEFAULT_LOCALE)
            chosenLanguage = loadResourceBundle(locale)
        }

        private fun loadResourceBundle(locale: Locale): ResourceBundle{
            val cl = URLClassLoader(ClassLoader.getSystemResources(RESOURCE_BUNDLE_NAME).toList().toTypedArray())
            return ResourceBundle.getBundle("language", locale, cl)
        }

        /**
         *
         */
        fun setLocale(locale: Locale){
            Translator.locale = locale
            chosenLanguage = loadResourceBundle(locale)
        }

        /**
         *
         */
        fun get(key: String): String {
            val chosenLang = chosenLanguage.keys.toList().firstOrNull { it == key }
            if(chosenLang != null) return chosenLanguage[chosenLang]

            val defaultLang = defaultLanguage.keys.toList().firstOrNull { it == key }
            if(defaultLang != null) return defaultLanguage[defaultLang]

            return ":$key:"
        }
    }

}