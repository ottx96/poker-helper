package de.ott.poker.ui

import javafx.geometry.Orientation
import tornadofx.*
import tornadofx.Form

class DetailForm : View("Details") {
    override val root = form {

        fieldset("Details") {

            field(orientation = Orientation.VERTICAL, text = "Derzeitiges Blatt") {
                label("Test")
            }
            field(orientation = Orientation.VERTICAL, text = "Bestes Blatt") {
                label("Test")
            }
        }

    }
}
