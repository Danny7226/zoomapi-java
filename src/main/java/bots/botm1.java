package bots;

import lib.cache.databaseData.Channel;
import lib.cache.databaseData.ChannelMember;
import lib.cache.databaseData.ChannelMessage;
import lib.oauth.OauthChannel;
import lib.oauth.OauthMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import lib.clients.OauthZoomClient;
import lib.components.ChatChannelsComponent;
import lib.components.ChatMessagesComponent;
import lib.utils.Util;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class botm1{
    public static void main (String args[]){
        OauthBot bot1 = new OauthBot("src/main/java/bots/bot.ini");
        while (true) {
            printOptions();
            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();
            switch (option) {
                case "0":
                    return;
                case "1":
                    listUserChannels(bot1);
                    break;
                case "2":
                    createChannel(bot1);
                    break;
                case "3":
                    getChannel(bot1);
                    break;
                case "4":
                    updateChannel(bot1);
                    break;
                case "5":
                    deleteChannel(bot1);
                    break;
                case "6":
                    listChannelMembers(bot1);
                    break;
                case "7":
                    inviteChannelMembers(bot1);
                    break;
                case "8":
                    joinChannel(bot1);
                    break;
                case "9":
                    leaveChannel(bot1);
                    break;
                case "10":
                    removeMember(bot1);
                    break;
                case "11":
                    listMessages(bot1);
                    break;
                case "12":
                    sendMessage(bot1);
                    break;
                case "13":
                    updateMessage(bot1);
                    break;
                case "14":
                    deleteMessage(bot1);
                    break;
                default:
                    System.out.println("Please provide a valid input...\n");
                    break;
            }
        System.out.println("Press any to continue.");
        sc.nextLine();
        }
    }

    private static void listUserChannels(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        List<Channel> channels = null;
        System.out.println("Use cache? (Y/N)");
        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();

        if(option.toLowerCase().equals("n")) channels = oc.listChannels();
        else if(option.toLowerCase().equals("y")) channels = oc.listChannels(true);
        else {
            System.out.println("Please provide a valid input...");
            listUserChannels(bot);
            return;
        }
        System.out.println("=== All Channels ===");
        if(channels==null){
            System.out.println("failed");
            return;
        }
        for(int i = 0; i<channels.size(); i++){
            System.out.println(channels.get(i));
        }
    }

    private static void createChannel(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a name for your new channel: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Provide a type for your new channel: ");
        System.out.println("1. Private & Invite only");
        System.out.println("2. Private & People from same organization invited only ");
        System.out.println("3. Public ");
        System.out.println("0. exit ");
        String type = sc.nextLine();
        Channel c = null;
        if (type.equals("0")) {
            return;
        } else if (type.equals("1")||type.equals("2")||type.equals("3")) {
            c = oc.createChannel(name, type);
            if(c!=null) {
                System.out.println("Channel created.");
                System.out.println(c.toString());
            }
            return;
        } else {
            System.out.println("Please provide a valid input...");
            return;
        }

    }

    private static void getChannel(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid Channel Name to get channel: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Use cache? (Y/N)");
        String option = sc.nextLine();
        Channel c = null;
        if(option.toLowerCase().equals("n")){
            c = oc.getChannel(name);
        }else if(option.toLowerCase().equals("y")){
            c = oc.getChannel(name, true);
        }else {
            System.out.println("Please provide a valid input...");
            getChannel(bot);
            return;
        }
        if(c!=null) System.out.println(c.toString());
        else{
            System.out.println("Failed");
        }
    }

    private static void updateChannel(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel Name to update channel: ");
        Scanner sc = new Scanner(System.in);
        String cName = sc.nextLine();
        System.out.println("Enter new channel name: ");
        String newCName = sc.nextLine();

        Channel c = oc.updateChannel(cName, newCName);
        if(c!=null){
            System.out.println("Channel Updated.");
            System.out.println(c.toString());
        }else{
            System.out.println("Failed");
        }
    }

    private static void deleteChannel(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel name to delete channel: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Channel c = oc.deleteChannel(name);
        if(c!=null){
            System.out.println("Channel Deleted.");
            System.out.println(c.toString());
        }else{
            System.out.println("Failed");
        }
    }

    private static void listChannelMembers(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel name to list current members: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Use cache? (Y/N)");
        String option = sc.nextLine();
        List<ChannelMember> members = null;
        if(option.toLowerCase().equals("n")){
            members = oc.listChannelMembers(name);
        }else if(option.toLowerCase().equals("y")){
            members = oc.listChannelMembers(name, true);
        }else {
            System.out.println("Please provide a valid input...");
            getChannel(bot);
            return;
        }
        if(members!=null){
            System.out.println("=== All Members ===");
            for(int i=0; i<members.size(); i++){
                System.out.println(members.get(i));
            }
        }else{
            System.out.println("Failed");
        }
    }

    private static void inviteChannelMembers(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel Name to invite new members: ");
        Scanner sc = new Scanner(System.in);
        String cName = sc.nextLine();
        List<String> members = new ArrayList<>();
        int member_length = 0;
        while (member_length < 5) {
            System.out.println(String.format("Provide a valid email address to invite members (type 'stop' to stop adding). Current members %s: ", member_length+1));
            String member = sc.nextLine();
            if (member.toLowerCase().equals("stop")) {
                break;
            }else {
                members.add(member);
                member_length++;
            }
        }
        String[] emails = new String[members.size()];
        List<ChannelMember> res = oc.inviteChannelMembers(cName, members.toArray(emails));
        if(res!=null){
            System.out.println("Succeeded");
            for(int i = 0; i<res.size();i++){
                System.out.println(res.get(i).toString());
            }
        }else{
            System.out.println("Failed");
        }
    }

    private static void joinChannel(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel ID of the channel that you want to join: ");
        Scanner sc = new Scanner(System.in);
        String cid = sc.nextLine();
        Channel c = oc.joinChannel(cid);
        if(c!=null){
            System.out.println("Succeeded");
            System.out.println(c.toString());
        }else{
            System.out.println("Failed");
        }
    }

    private static void leaveChannel(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel name of the channel that you want to leave: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Channel c = oc.leaveChannel(name);
        if(c!=null){
            System.out.println("Succeeded");
            System.out.println(c.toString());
        }else{
            System.out.println("Failed");
        }

    }

    private static void removeMember(OauthBot bot) {
        OauthChannel oc = bot.getChannel();
        System.out.println("Provide a valid channel name of the channel to remove a member from: ");
        Scanner sc = new Scanner(System.in);
        String cName = sc.nextLine();
        System.out.println("Provide a valid member email of the member to remove: ");
        String email = sc.nextLine();
        ChannelMember cm = oc.removeMember(cName, email);
        if(cm!=null){
            System.out.println("Succeeded");
            System.out.println(cm.toString());
        }else{
            System.out.println("Failed");
        }
    }

    private static void listMessages(OauthBot bot) {
        OauthMessage om = bot.getMessage();
        System.out.println("List messages:");
        System.out.println("1. In a chat channel");
        System.out.println("2. Between you and a contact");
        System.out.println("0. Exit");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        System.out.println("Make sure start date and to date are no more than 5 days apart!");
        System.out.println("Enter start date (yyyy-mm-dd): ");
        String fromDate = sc.nextLine();
        System.out.println("Enter end date (yyyy-mm-dd): ");
        String toDate = sc.nextLine();
        List<ChannelMessage> historyList = null;
        switch (input) {
            case "0": break;
            case "1": ;
                System.out.println("Enter chat channel: ");
                String toChannel = sc.nextLine();
                System.out.println("Use cache? (Y/N)");
                String option = sc.nextLine();
                if(option.toLowerCase().equals("n")) historyList = om.getChannelMessages(toChannel, fromDate, toDate);
                else if(option.toLowerCase().equals("y")) historyList = om.getChannelMessages(toChannel,fromDate,toDate,true);
                int number = 0;
                System.out.println("=== All Messages ===");
                for(ChannelMessage m:historyList){
                    number++;
                    System.out.println(number + ". " + m.toString());
                }
                break;
            case "2":
                System.out.println("Enter contact email: ");
                String toContact = sc.nextLine();
                System.out.println("Use cache? (Y/N)");
                String option2 = sc.nextLine();
                if(option2.toLowerCase().equals("n")) historyList = om.getMemberMessages(toContact, fromDate, toDate);
                else if(option2.toLowerCase().equals("y")) historyList = om.getMemberMessages(toContact,fromDate,toDate,true);
                int member = 0;
                System.out.println("=== All Messages ===");
                for(ChannelMessage m:historyList){
                    member++;
                    System.out.println(member + ". " + m.toString());
                }
                break;
            default:
                System.out.println("Pick from available options");
                return;
        }
    }

    private static void sendMessage(OauthBot bot) {
        OauthMessage om = bot.getMessage();
        System.out.println("Send messages:");
        System.out.println("1. In a chat channel");
        System.out.println("2. Between you and a contact");
        System.out.println("0. Exit");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input) {
            case "0": break;
            case "1":
                System.out.println("Enter channel name: ");
                String toChannel = sc.nextLine();
                System.out.println("Enter message to send: ");
                String message = sc.nextLine();
                boolean response = om.sendChatToChannel(toChannel, message)!=null;
                if (response) {
                    System.out.println("Message Sent.");
                } else {
                    System.out.println("Message Not Sent.");
                }
                break;
            case "2":
                System.out.println("Enter contact email: ");
                String toContact = sc.nextLine();
                System.out.println("Enter message to send: ");
                String message2 = sc.nextLine();
                boolean response2 = om.sendChatToMember(toContact, message2)!=null;
                if (response2) {
                    System.out.println("Message Sent.");
                } else {
                    System.out.println("Message Not Sent.");
                }
                break;
            default:
                System.out.println("Pick from available options");
                return;
        }
    }

    private static void updateMessage(OauthBot bot) {
        OauthMessage om = bot.getMessage();
        System.out.println("Update messages:");
        System.out.println("1. In a chat channel");
        System.out.println("2. Between you and a contact");
        System.out.println("0. Exit");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input) {
            case "0": break;
            case "1":
                System.out.println("Enter channel name: ");
                String toChannel = sc.nextLine();
                System.out.println("Enter messageId to update: ");
                String messageId = sc.nextLine();
                System.out.println("Enter message to update: ");
                String message = sc.nextLine();
                boolean response = om.updateMessageFromChannel(toChannel, messageId, message)!=null;
                if (response) {
                    System.out.println("Message Update Succeeded.");
                } else {
                    System.out.println("Message Update Failed.");
                }
                break;
            case "2":
                System.out.println("Enter contact email: ");
                String toContact = sc.nextLine();
                System.out.println("Enter messageId to update: ");
                String messageId2 = sc.nextLine();
                System.out.println("Enter message to update: ");
                String message2 = sc.nextLine();
                boolean response2 = om.updateMessageSentToMember(toContact, messageId2, message2)!=null;
                if (response2) {
                    System.out.println("Message Update Succeeded.");
                } else {
                    System.out.println("Message Update Failed.");
                }
                break;
            default:
                System.out.println("Pick from available options");
                return;
        }
    }

    private static void deleteMessage(OauthBot bot) {
        OauthMessage om = bot.getMessage();
        System.out.println("Delete messages:");
        System.out.println("1. In a chat channel");
        System.out.println("2. Between you and a contact");
        System.out.println("0. Exit");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input) {
            case "0": break;
            case "1":
                System.out.println("Enter channel name: ");
                String toChannel = sc.nextLine();
                System.out.println("Enter messageId to delete: ");
                String messageId = sc.nextLine();
                boolean response = om.deleteMessageFromChannel(toChannel, messageId)!=null;
                if (response) {
                    System.out.println("Message Delete Succeeded.");
                } else {
                    System.out.println("Message Delete Failed.");
                }
                break;
            case "2":
                System.out.println("Enter contact email: ");
                String toContact = sc.nextLine();
                System.out.println("Enter messageId to delete: ");
                String messageId2 = sc.nextLine();
                boolean response2 = om.deleteMessageSentToMember(toContact, messageId2)!=null;
                if (response2) {
                    System.out.println("Message Delete Succeeded.");
                } else {
                    System.out.println("Message Delete Failed.");
                }
                break;
            default:
                System.out.println("Pick from available options");
                return;
        }
    }

    private static void printOptions() {
        System.out.println("Please type in your command with a valid numeric index(0 ~ 14): ");
        System.out.println("========== Channels ===========");
        System.out.println("1. List User's Channels");
        System.out.println("2. Create a Channel");
        System.out.println("3. Get a Channel");
        System.out.println("4. Update a Channel");
        System.out.println("5. Delete a Channel");
        System.out.println("6. List Channel Members");
        System.out.println("7. Invite Channel Members");
        System.out.println("8. Join a Channel");
        System.out.println("9. Leave a Channel");
        System.out.println("10. Remove a Member");
        System.out.println("========== Messages ===========");
        System.out.println("11. List User's Chat Messages");
        System.out.println("12. Send a Chat Messages");
        System.out.println("13. Update a Message");
        System.out.println("14. Delete a Message");
        System.out.println("0. Exit");
    }
}