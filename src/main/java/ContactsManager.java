import java.util.*;
import java.util.function.Predicate;

public class ContactsManager {
    private List<Contact> contacts;
    private HashMap<String, List<Contact>> labels;

    public ContactsManager() {
        contacts = new LinkedList<>();
        labels = new HashMap<>(200);
    }

    public List<String> getLabels() {
        return this.labels.keySet().stream().toList();
    }
    public List<Contact> getContacts(String... labels) {
        // TODO get contacts in labels or all ...
        List<Contact> contacts = new LinkedList<>();

        if(labels.length == 0){
            return this.contacts;
        }

        for(var label: labels){
             if(this.labels.containsKey(label)){
                 contacts.addAll(this.labels.get(label));
             }
         }

        return contacts;
    }
    public List<Contact> search(String term, String... labels) {
        // TODO search for contacts with term, and also in specific labels
        LinkedList<Contact> contacts = new LinkedList<>();

        if(term == null){
            return this.contacts;
        }
        if(labels.length != 0){
            for(var label : labels){
                if(this.labels.containsKey(label)){
                    for (var contact: this.contacts){
                        if(contact.getFirstName().contains(term) || contact.getLastName().contains(term) ||
                                contact.getEmail().contains(term) || contact.getPhone().contains(term) || contact.getBirthday().toString().contains(term)){
                            contacts.add(contact);
                        }
                    }
                }
            }
        }else{
            for(var contact : contacts){
                if(contact.getFirstName().contains(term) || contact.getLastName().contains(term) ||
                        contact.getEmail().contains(term) || contact.getPhone().contains(term) || contact.getBirthday().toString().contains(term)){
                    contacts.add(contact);
                }
            }
        }

         return contacts;
    }
    public void addContact(Contact contact, String... labels) {
        Predicate<Contact> duplicate = c -> Objects.equals(c.getPhone(),
                contact.getPhone()) && Objects.equals(c.getEmail(), contact.getEmail());

        if (contacts.stream().noneMatch(duplicate)) contacts.add(contact);
        if (labels.length == 0) return;
        for (var label : labels) {
            if (!this.labels.containsKey(label)) {
                this.labels.put(label, new LinkedList<>());
            }
            var contactsLabel = this.labels.get(label);
            if (!contacts.stream().noneMatch(duplicate)) {
                contactsLabel.add(contact);
            }
        }


    }
    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
        this.labels.values().forEach(contacts -> contacts.remove(contact));
    }
    public int size() {
        return contacts.size();
    }
}
