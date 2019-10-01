import org.junit.Test;
public class Test{
    @Test(expected=GameWinException.java)
    public void test1(){
        System.out.println("Testing GameWinException");
        Game g = new game("Ajay");
    }
    @Test(expected=VultureBiteException.java)
    public void test2(){
        System.out.println("Testing VultureBiteException");
        Game g = new game("Ajay");
    }
    @Test(expected=TrampolineException.java)
    public void test3(){
        System.out.println("Testing TrampolineException");
        Game g = new game("Ajay");
    }
    @Test(expected=CricketBiteException.java)
    public void test4(){
        System.out.println("Testing CricketBiteException");
        Game g = new game("Ajay");
    }
    @Test(expected=SnakeBiteException.java)
    public void test5(){
        System.out.println("Testing SnakeBiteException");
        Game g = new game("Ajay");
    }
}