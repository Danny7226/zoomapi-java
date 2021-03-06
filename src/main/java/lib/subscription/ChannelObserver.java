package lib.subscription;

import lib.cache.databaseData.CacheUnit;
import lib.clients.OauthZoomClient;
import lib.cache.databaseData.Unit;

import java.util.HashSet;
import java.util.Set;

public class ChannelObserver{
    private String observerName;
    private String channelName;
    private OauthZoomClient client;
    private Set<Integer> subscription;
    private String fromDate;
    private String toDate;

    public ChannelObserver(String observerName, OauthZoomClient client, String channelName) {
        this.channelName = channelName;
        this.client = client;
        subscription = new HashSet<>();
        this.observerName = observerName;


    }
    public String getChannelName(){
        return this.channelName;
    }

    public OauthZoomClient getClient(){
        return this.client;
    }

    public void setObservingDate(String fromDate, String toDate){
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String[] getObservingDate(){
        return new String[]{this.fromDate, this.toDate};
    }

    private void checkObservingDate(){
        if(fromDate == null || toDate == null){
            throw new IllegalStateException("Please specify observing date by setObservingDate()");
        }
    }
    public void subscribeTo(int eventCode){
        if(!SubscribeAgency.getStatus()) SubscribeAgency.initialize();
        checkObservingDate();
        SubscribeAgency.subscribeTo(eventCode, this);
        subscription.add(eventCode);
    }

    public void unsubscribeFrom(int eventCode){
        SubscribeAgency.unsubscribeFrom(eventCode, this);
        subscription.remove(eventCode);
    }

    /************************************************
     * call-back method to be called once data changes
     ************************************************/
    public void update(CacheUnit e){
        System.out.println(observerName + ": " + e.toString());
    }
}
