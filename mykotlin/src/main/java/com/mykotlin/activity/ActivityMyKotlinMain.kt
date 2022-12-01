package com.mykotlin.activity


class ActivityMyKotlinMain {
    open class Animal {
        open fun shout() = println("animal is shout")//定义成员函数
    }

    class Cat: Animal() {
        override fun shout() {
            println("Cat is shout")//子类重写父类成员函数
        }
    }

    //定义子类和父类扩展函数
    fun Animal.eat() = println("Animal eat something")

    fun Cat.eat()= println("Cat eat fish")

    fun BaseActivity.Oncreate()=apply {
        println("啥玩意啊？")
    }



    //测试
    fun main(args: Array<String>) {
        val animal: Animal = Cat()
        println("成员函数测试: ${animal.shout()}")
        println("扩展函数测试: ${animal.eat()}")
    }

}