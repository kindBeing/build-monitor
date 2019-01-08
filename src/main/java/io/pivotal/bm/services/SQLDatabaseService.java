package io.pivotal.bm.services;

import io.pivotal.bm.BMRepository;
import io.pivotal.bm.models.PREntry;
import io.pivotal.bm.models.RepoInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLDatabaseService implements BMRepository {
    private JdbcTemplate jdbcTemplate;

    public SQLDatabaseService(DataSource dataSource) {
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
    public void createRepo(RepoInfo repoInfo) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO git_repository (status, build_state, build_color, commit_difference) VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setString(1, repoInfo.getStatus());
            statement.setString(2, repoInfo.getBuildState());
            statement.setString(3, repoInfo.getBuildColor());
            statement.setInt(4, repoInfo.getCountCommitDifference());

            return statement;
        }, generatedKeyHolder);
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
    public int countUnmerged() {
        ResultSetExtractor<Integer> countExtractor = new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                return rs.next() ? rs.getInt("count") : 0;
            }
        };
        return jdbcTemplate.query("SELECT COUNT(*) as count FROM pull_requests WHERE status='UNMERGED'", countExtractor);
    }

    @Override
    public int countUnknown() {
        ResultSetExtractor<Integer> countExtractor = rs -> rs.next() ? rs.getInt("count") : 0;
        return jdbcTemplate.query("SELECT COUNT(*) as count FROM pull_requests WHERE status='UNKNOWN'", countExtractor);
    }

    @Override
    public RepoInfo getRepoInfo() {
        ResultSetExtractor<RepoInfo> repoExtractor = rs -> {
            int countCommitDifference = rs.getInt("commit_difference");

            RepoInfo repoInfo = new RepoInfo(rs.getString("status"),
                    rs.getString("build_state"),
                    rs.getString("build_color"),
                    countCommitDifference);
            return repoInfo;
        };
        return jdbcTemplate.query("SELECT  * FROM repo_info", repoExtractor);
    }

    @Override
    public List<PREntry> list() {
        return jdbcTemplate.query("SELECT prid, status FROM pull_requests", mapper);
    }

    @Override
    public List<PREntry> listUnmerged() {
        return jdbcTemplate.query("SELECT prid, status FROM pull_requests WHERE status != 'MERGED'", mapper);
    }

    private final RowMapper<PREntry> mapper = (rs, rowNum) -> new PREntry(
            rs.getString("prid"),
            rs.getString("status")
    );

    private final ResultSetExtractor<PREntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
