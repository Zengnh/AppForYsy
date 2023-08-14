package com.appforysy.activity.activity_main.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var listData = arrayListOf<ItemBookTxt>()
    init {
        val book = ItemBookTxt()
        book.bookName = "本草纲目"
        book.bookRemark = "《本草纲目》，本草著作，52卷。明代李时珍(东璧)撰于嘉靖三十一年(1552年)至万历六年(1578年)，稿凡三易。"
        book.bookPath="bcgm.txt"
        listData.add(book)

        val book2 = ItemBookTxt()
        book2.bookName = "伤寒论"
        book2.bookRemark ="《伤寒论》为东汉张仲景所著汉医经典著作，是一部阐述外感热病治疗规律的专著，全书12卷。现今遗存10卷22篇。张仲景原著《伤寒杂病论》在流传的过程中，经后人整理编纂将其中外感热病内容结集为《伤寒论》，另一部分主要论述内科杂病，名为《金匮要略方论》。"
        book2.bookPath="shanghanlun.txt"
        listData.add(book2)

        val book3 = ItemBookTxt()
        book3.bookName = "神农本草经"
        book3.bookRemark =
            "《神农本草经》又称《本草经》或《本经》，托名“神农”所作，实成书于汉代，是中医四大经典著作之一，是已知最早的中药学著作。《神农本草经》全书分三卷，载药365种，以三品分类法，分上、中、下三品，文字简练古朴，成为中药理论精髓。"
        book3.bookPath="snbcj.txt"
        listData.add(book3)

        val book4 = ItemBookTxt()
        book4.bookName = "黄帝内经"
        book4.bookRemark =
            "《黄帝内经》又称《内经》，是中国最早的典籍之一，也是中国传统医学四大经典之首。相传为黄帝所作，因以为名。但后世较为公认此书最终成型于西汉，作者亦非一人，而是由中国历代黄老医家传承增补发展创作而来。"
        book4.bookPath="hdnjgwyb.txt"
        listData.add(book4)

        val book5 = ItemBookTxt()
        book5.bookName = "难经"
        book5.bookRemark =
            "《难经》原名《黄帝八十一难经》，又称《八十一难》，是中医现存较早的经典著作。关于《难经》的作者与成书年代历来有不同的看法，一般认为其成书不晚于东汉，内容可能与秦越人（扁鹊）有一定关系。《难经》之“难”字，有“问难”或“疑难”之义。全书共八十一难，采用问答方式，探讨和论述了中医的一些理论问题"
        book5.bookPath="bsynj.txt"
        listData.add(book5)


        val book6 = ItemBookTxt()
        book6.bookName = "灵枢经"
        book6.bookRemark =
            "《灵枢经》，又称《灵枢》《针经》《九针》，中国传统医学重要著作之一，中医学理论体系形成和奠基之作。是现存最早的中医理论著作，约成书于东周战国时期。"
        book6.bookPath="lingshujin.txt"
        listData.add(book6)

        val book7 = ItemBookTxt()
        book7.bookName = "兰室秘藏"
        book7.bookRemark =
            "《兰室秘藏》，三卷，元·李杲撰，刊于公元1276年（元朝至元十三年）。书名“兰室”，取《素问·灵兰秘典论》“藏灵兰之室”一语，表示所载方论有珍藏的价值。现存元刻本及多种明清刻本，并收入《济生拔萃》《东垣十书》《古今医统正脉全书》等丛书中，1949年后有影印本和排印本。"
        listData.add(book7)

        val book8 = ItemBookTxt()
        book8.bookName = "金匮要略"
        book8.bookRemark =
            "《金匮要略》是我国东汉著名医学家张仲景所著《伤寒杂病论》的杂病部分，也是我国现存最早的一部论述杂病诊治的专书，原名《金匮要略方论》。“金匮”是存放古代帝王圣训和实录的地方，意指本书内容之珍贵。"
        book8.bookPath="jinkuiyaolue.txt"
        listData.add(book8)


        val book9 = ItemBookTxt()
        book9.bookName = "景岳全书"
        book9.bookRemark = "《景岳全书》，明代张介宾撰，六十四卷。首选《内经》《难经》《伤寒》《金匮》之论，博采历代医家精义，并结合作者经验，自成一家之书。《全书》成于景岳晚年，在其殁后刊行。"
        listData.add(book9)


        val book10 = ItemBookTxt()
        book10.bookName = "类经"
        book10.bookRemark = "《类经》是明代张景岳所著的医经著作，也是继隋代杨上善《太素》之后，对《内经》进行全面分类研究的又一著作，他将《灵枢》《素问》分作十二大类，三百九十节，共计三十二卷"
        listData.add(book10)

        val book11 = ItemBookTxt()
        book11.bookName = "脉经"
        book11.bookRemark = "《脉经》脉学著作，西晋·王叔和撰于公元3世纪，10卷。集汉以前脉学之大成，选录《内经》《难经》《伤寒论》《金匮要略》及扁鹊、华佗等有关脉学之论说。《脉经》系我国现存较早的脉学专书，首次系统归纳了24种脉象，对其性状作出具体描述，初步肯定了有关三部脉的定位诊断，为后世脉学发展奠定基础，并有指导临床实践之意义。"
        listData.add(book11)
    }
}