import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tasol on 28/11/14.
 */
public class TestQuestion
{
    protected Map<Integer,String> que;
    protected Map<Integer,String> ans;


    public TestQuestion()
    {
        this.que = new HashMap<Integer,String>();
        this.ans = new HashMap<Integer,String>();
    }

    public String getQue(Integer id) {
        return this.que.get(id);
    }

    public void setQue(Integer id, String text) {
        this.que.put(id,text);
    }

    public String getAns(Integer id) {
        return this.ans.get(id);
    }

    public void setAns(Integer id, String text) {
        this.ans.put(id,text);
    }
}
