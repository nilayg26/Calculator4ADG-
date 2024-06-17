package com.example.calculator4adg

interface Destinations{
    val route:String
}
object WelcomePage:Destinations{
    override val route: String="WelcomePage"
}
object Screen1:Destinations{
    override val route: String="Screen1"
}
object Screen2:Destinations{
    override val route: String="Screen2"
}

