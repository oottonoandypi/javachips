package javachips;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;

public class MiniTwitter {
	private HashMap<Integer, HashSet<Integer>> users;
    private ArrayList<Integer> posts;
    private ArrayList<Integer> postedBy;
    
    public MiniTwitter() {
        this.users=new HashMap<Integer, HashSet<Integer>>();
        this.posts=new ArrayList<Integer>();
        this.postedBy=new ArrayList<Integer>();
    }
    
    public void postTweet(int userId, int tweetId) {
        this.posts.add(tweetId);
        this.postedBy.add(userId);
        if(!this.users.containsKey(userId)) {
            this.users.put(userId, new HashSet<Integer>());
            this.users.get(userId).add(userId);
        }
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> newsFeed=new ArrayList<Integer>();
        if(!users.containsKey(userId)) return newsFeed;
        
        HashSet<Integer> following = users.get(userId);
        for(int i=this.posts.size()-1; i>=0 && newsFeed.size()<10; i--){
            if(following.contains(this.postedBy.get(i))) newsFeed.add(this.posts.get(i));
        }
        
        return newsFeed;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!this.users.containsKey(followerId)) {
            this.users.put(followerId, new HashSet<Integer>());
            this.users.get(followerId).add(followerId);
        }
        this.users.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!this.users.containsKey(followerId)) {
            this.users.put(followerId, new HashSet<Integer>());
            this.users.get(followerId).add(followerId);
        }
        if(this.users.get(followerId).contains(followeeId)) this.users.get(followerId).remove(followeeId);
    }
}
