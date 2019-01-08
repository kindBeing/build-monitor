package io.pivotal.bm;

import io.pivotal.bm.models.PREntry;
import org.springframework.context.annotation.Profile;

import java.util.List;

public class InMemoryService implements BMRepository {
    @Override
    public void create(PREntry entry) {

    }

    @Override
    public void create(Iterable<PREntry> entries) {

    }

    @Override
    public void update(PREntry entry) {

    }

    @Override
    public void remove(PREntry entry) {

    }

    @Override
    public List<PREntry> list() {
        return null;
    }

    @Override
    public List<PREntry> listUnmerged() {
        return null;
    }
}
