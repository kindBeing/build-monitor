package io.pivotal.bm.services;

import io.pivotal.bm.domain.PRDBRepository;
import io.pivotal.bm.models.PREntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PRDBService implements PRDBRepository {

    private JdbcTemplate jdbcTemplate;

    public PRDBService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(PREntry entry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO pull_requests (prid, status) VALUES (?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setString(1, entry.getPrId());
            statement.setString(2, entry.getStatus());

            return statement;
        }, generatedKeyHolder);
//        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public int countUnmerged() {
        ResultSetExtractor<Integer> countExtractor = rs -> rs.next() ? rs.getInt("count") : 0;
        return jdbcTemplate.query("SELECT COUNT(*) as count FROM pull_requests WHERE status='UNMERGED'", countExtractor);
    }

    @Override
    public int countUnknown() {
        ResultSetExtractor<Integer> countExtractor = rs -> rs.next() ? rs.getInt("count") : 0;
        return jdbcTemplate.query("SELECT COUNT(*) as count FROM pull_requests WHERE status='UNKNOWN'", countExtractor);
    }

    @Override
    public List<PREntry> list() {
        return jdbcTemplate.query("SELECT prid, status FROM pull_requests", mapper);
    }

    @Override
    public List<PREntry> listUnmerged() {
        return jdbcTemplate.query("SELECT prid, status FROM pull_requests WHERE status != 'MERGED'", mapper);
    }

    @Override
    public void updateUnmerged(List<PREntry> newPREntries) {
        for(PREntry entry: newPREntries){
            update(entry);
        }
    }

    private void update(PREntry entry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE pull_requests SET status=? WHERE prid=?)",
                    RETURN_GENERATED_KEYS
            );
            statement.setString(1, entry.getStatus());
            statement.setString(2, entry.getPrId());
            return statement;
        }, generatedKeyHolder);
    }

    private final RowMapper<PREntry> mapper = (rs, rowNum) -> new PREntry(
            rs.getString("prid"),
            rs.getString("status")
    );

    private final ResultSetExtractor<PREntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
