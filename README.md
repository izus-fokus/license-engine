
# License-Engine

## Build

Run `mvn package` to build a Fat JAR.

## Run (Standalone)

Use `docker-compose up` to start the License-Engine as well as a Fossology instance using Docker. 

### Endpoints
- License-Engine: `http://localhost:7000/swagger-ui.html`
- Fossology: `http://localhost:7100/repo`

## Integration (in GitHub workflows)

The License-Engine can be used within a GitHub workflow to check if the used licenses are compatible (i) to each other and (ii) to the defined license of the repository.

### Usage
```
jobs:
  call-license-check-workflow:
    uses: OpenTOSCA/license-engine/.github/workflows/license-check.yml@main
```
### Ignore files

In case some files should be ignored, e.g. because they are only used for testing or building the software, a `.license_ignorefiles` file can be placed in the root directory.
The files to be ignored should be listed line by line. `*` can be used as a placeholder.

Examples:<br/>
To ignore the file `/src/test/helloWorldTest.java`, the `.license_ignorefiles` file should have this entry:
`/src/test/helloWorldTest.java`
<br/>
To ignore all `.java` files, the `.license_ignorefiles` file should have this entry:
`*.java`
<br/>
To ignore the specific folder `/test/`, the `.license_ignorefiles` file should have this entry:
`*/test/*`

## Haftungsausschluss

Dies ist ein Forschungsprototyp.
Die Haftung für entgangenen Gewinn, Produktionsausfall, Betriebsunterbrechung, entgangene Nutzungen, Verlust von Daten und Informationen, Finanzierungsaufwendungen sowie sonstige Vermögens- und Folgeschäden ist, außer in Fällen von grober Fahrlässigkeit, Vorsatz und Personenschäden, ausgeschlossen.

## Disclaimer of Warranty

Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.
