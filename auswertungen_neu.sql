


---ZEITAUSWERTUNGEN
---Desicions over Time by Month
SELECT
    TO_CHAR(random_date, 'YYYY-MM') AS year_month,
    COUNT(*) AS decision_count
FROM
    adr_entities
GROUP BY
    TO_CHAR(random_date, 'YYYY-MM')
ORDER BY
    year_month;


---Entscheidungsstatus nach Monat
SELECT
    TO_CHAR(random_date, 'YYYY-MM') AS year_month,
    COUNT(CASE WHEN status = 'Deprecated' THEN 1 END) AS count_deprecated,
    COUNT(CASE WHEN status = 'Active' THEN 1 END) AS count_active
FROM
    adr_entities
GROUP BY
    TO_CHAR(random_date, 'YYYY-MM')
ORDER BY
    year_month;

---Desicions over Time


---Häufigkeit der Artefakte DESC
SELECT
    SUBSTRING(
        a.text,
        position('[' in a.text) + 1,
        position(']' in a.text) - position('[' in a.text) - 1
    ) AS artifact_in_brackets,
    COUNT(*) AS count
FROM
    artifact a
JOIN
    adr_entities_artifacts ae ON a.id = ae.artifacts_id
WHERE
    position('[' in a.text) > 0 AND position(']' in a.text) > position('[' in a.text)
GROUP BY
    artifact_in_brackets
ORDER BY
    count DESC;

---ADR's nutzen die gleichen Artefakte
SELECT
    SUBSTRING(
        a.text,
        position('[' in a.text) + 1,
        position(']' in a.text) - position('[' in a.text) - 1
    ) AS artifact_in_brackets,
    STRING_AGG(CAST(ae.adr_entities_id AS VARCHAR), ',') AS adr_ids
FROM
    artifact a
JOIN
    adr_entities_artifacts ae ON a.id = ae.artifacts_id
WHERE
    position('[' in a.text) > 0 AND position(']' in a.text) > position('[' in a.text)
GROUP BY
    artifact_in_brackets
ORDER BY
    artifact_in_brackets;


---Relations between ADR's
SELECT
    aer.adr_entities_id AS ADRID,
    STRING_AGG(CASE WHEN r.txt LIKE 'enables %%' THEN regexp_result[1] END, ', ') AS enables,
    STRING_AGG(CASE WHEN r.txt LIKE 'is enabled by %%' THEN regexp_result[1] END, ', ') AS enabled_by,
    STRING_AGG(CASE WHEN r.txt LIKE 'is extended by %%' THEN regexp_result[1] END, ', ') AS extended_by,
    STRING_AGG(CASE WHEN r.txt LIKE 'is deprecated by %%' THEN regexp_result[1] END, ', ') AS deprecated_by
FROM
    adr_entities_relations aer
JOIN
    relation r ON aer.relations_id = r.id
LEFT JOIN LATERAL
    REGEXP_MATCHES(r.txt, 'ADR ([0-9]+)') AS regexp_result ON true
GROUP BY
    aer.adr_entities_id
ORDER BY
    ADRID;