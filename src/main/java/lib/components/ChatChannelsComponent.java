package lib.components;

import org.json.JSONObject;
import lib.utils.Util;

import java.util.Map;

public class ChatChannelsComponent extends BaseComponent{
    public ChatChannelsComponent(String base_uri, Map<String, String> config){
        super(base_uri, config);
    }

    public JSONObject listChannels(Map<String, String> params){
        return this.getRequest("/chat/users/me/channels", params);
    }

    public JSONObject createChannel(Map<String, String> data){
        String[] keys = new String[]{"name", "type"};
        Util.requireKeys(data, keys);
        return this.postRequest("/chat/users/me/channels", data);
    }

    public JSONObject getChannel(Map<String, String> params){
        String[] keys = new String[] {"channelId"};
        Util.requireKeys(params, keys);
        return this.getRequest(String.format("/chat/channels/%s", params.get("channelId")), params);
    }

    public JSONObject updateChannel(Map<String, String> data){
        String[] keys = new String[] {"channelId", "name"};
        Util.requireKeys(data, keys);
        return this.patchRequest(String.format("/chat/channels/%s", data.get("channelId")), data);
    }

    public JSONObject deleteChannel(Map<String, String> params){
        String[] keys = new String[] {"channelId"};
        Util.requireKeys(params, keys);
        return this.deleteRequest(String.format("/chat/channels/%s", params.get("channelId")), params);
    }

    public JSONObject listChannelMembers(Map<String, String> params){
        String[] keys = new String[] {"channelId"};
        Util.requireKeys(params, keys);
        return this.getRequest(String.format("/chat/channels/%s/members", params.get("channelId")), params);
    }

    public JSONObject inviteChannelMembers(Map<String, String> data){
        String[] keys = new String[] {"channelId", "members"};
        Util.requireKeys(data, keys);
        return this.postRequest(String.format("/chat/channels/%s/members", data.get("channelId")), data);
    }

    public JSONObject joinChannel(Map<String, String> data){
        String[] keys = new String[] {"channelId"};
        Util.requireKeys(data, keys);
        return this.postRequest(String.format("/chat/channels/%s/members/me", data.get("channelId")), data);
    }

    public JSONObject leaveChannel(Map<String, String> params){
        String[] keys = new String[] {"channelId"};
        Util.requireKeys(params, keys);
        return this.deleteRequest(String.format("/chat/channels/%s/members/me", params.get("channelId")), params);
    }

    public JSONObject removeMember(Map<String, String> params){
        String[] keys = new String[] {"channelId", "memberId"};
        Util.requireKeys(params, keys);
        return this.deleteRequest(String.format("/chat/channels/%s/members/%s", params.get("channelId"), params.get("memberId")), params);
    }
}
