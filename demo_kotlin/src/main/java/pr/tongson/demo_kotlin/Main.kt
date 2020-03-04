package pr.tongson.demo_kotlin

/**
 * <b>Create Date:</b> 2020-02-11<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * @author tongson
 */

fun main(args: Array<String>) {
    Regex(""".*(\d{3}-\d{8}.*)""").findAll("ddddd:010-12345678.666sfd..").toList().flatMap(MatchResult::groupValues).forEach(::println)
}
//
//val fibonacci = buildSequence {
//    yield(1)
//    var cur=1
//    var next=1
//    while (true){
//        yield(next)
//        val temp=cur+next
//        cur=next
//        next=temp
//    }
//}

