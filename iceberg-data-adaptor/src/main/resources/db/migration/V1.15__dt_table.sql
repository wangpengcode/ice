CREATE TABLE IF NOT EXISTS CAPITAL_DT
(
    ID BIGINT,
    CODE  VARCHAR(64) NOT NULL,
    DATE  VARCHAR(64) NOT NULL,
    CODE_NAME VARCHAR(64)  DEFAULT NULL,
    INDUSTRY VARCHAR(64) DEFAULT NULL,
    REMARK VARCHAR(64) DEFAULT NULL,
    LAST_MONTH BIGINT,
    LAST_TWO_MONTH BIGINT,
    LAST_THREE_MONTH BIGINT,
    LAST_HALF_YEAR BIGINT
);

ALTER TABLE CAPITAL_DT ADD CONSTRAINT CAPITAL_DT_UK UNIQUE (DATE,CODE);
