package splitwise.group;

import splitwise.user.User;

import java.util.ArrayList;
import java.util.List;

public class GroupController {
    List<Group> groupList;

    public GroupController() {
        groupList=new ArrayList<>();
    }

    public void createGroup(String groupId, String groupName, User createdByUser){
        Group group=new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        group.setAdmin(createdByUser);

        groupList.add(group);
    }
    public Group getGroupById(String  groupId){
        for(Group group:groupList){
            if(group.getGroupId()==groupId){
                return group;
            }
        }
        throw new IllegalArgumentException("Wrong Group Id!!");
    }
    public void addMemberToGroup(String groupId, User newUser,User admin) {
        Group group = getGroupById(groupId);
        if(admin!=group.isAdmin()){
            throw new RuntimeException("Only Admin can add the user!!");
        }
        if (group == null) {
            throw new IllegalArgumentException("Group not found: " + groupId);
        }
        if (newUser == null) {
            throw new IllegalArgumentException("Cannot add a null user");
        }
        if (group.getGroupList().contains(newUser)) {
            throw new IllegalArgumentException("User is already a member of this group");
        }
        group.addGroupMember(newUser);
    }
    public void removeMemberFromGroup(String groupId, User user) {
        Group group = getGroupById(groupId);
        // check user's net balance in this group is 0 before allowing removal
        group.removeMember(user);
    }
}
