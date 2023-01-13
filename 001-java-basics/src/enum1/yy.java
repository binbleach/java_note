package enum1;

public class yy {
    public static reason result(int a , int b){
        try{
            int c =a/b;
            return reason.SUCCESS;
        }catch(Exception e){
            return reason.FAIL;
        }
    }
}
