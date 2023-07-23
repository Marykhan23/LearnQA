
import org.junit.Assert
import org.junit.Test


class MainClassTest {
    @Test
    fun testGetLocalNumber(){
        Assert.assertTrue("Returns not number 14!", MainClass().getLocalNumber() == 14)
    }

    @Test
    fun testGetClassNumber(){
        Assert.assertTrue("Returns more than number 45!", MainClass().getClassNumber() <= 45)
    }

    @Test
    fun testGetClassString(){
        Assert.assertTrue("I coulnd't find \"hello\" in the string", MainClass().getClassString().uppercase().contains("hello".uppercase()))
    }


}