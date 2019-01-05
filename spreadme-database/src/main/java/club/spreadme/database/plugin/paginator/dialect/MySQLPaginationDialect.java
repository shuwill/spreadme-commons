/*
 *  Copyright (c) 2019 Wangshuwei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package club.spreadme.database.plugin.paginator.dialect;

public class MySQLPaginationDialect implements PaginationDialect {

    private static final String DATABASENAME = "mysql";

    @Override
    public boolean isTargetDatabase(String dataBaseName) {
        return DATABASENAME.equals(dataBaseName.toLowerCase());
    }

    @Override
    public String getPagenation(String sql, int offset, int limit) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        if (offset == 0) {
            sqlBuilder.append(" limit ");
            sqlBuilder.append(limit);
        } else {
            sqlBuilder.append(" limit ");
            sqlBuilder.append(offset);
            sqlBuilder.append(",");
            sqlBuilder.append(limit);
        }
        return sqlBuilder.toString();
    }

}
