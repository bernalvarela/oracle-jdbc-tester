# GitHub Workflows

This directory contains GitHub Actions workflows for automated building and releasing.

## Workflows

### 1. `maven-publish.yml` - Maven Package Publishing

**Trigger:** When a release is created

**Purpose:** Publishes the project to GitHub Packages Maven repository.

**Actions:**
- Builds the project with Maven
- Publishes to GitHub Packages

---

### 2. `release-all-versions.yml` - Build and Release All Driver Versions

**Triggers:**
- Automatically when a release is created
- Manually via workflow dispatch

**Purpose:** Compiles the project with 10 different Oracle JDBC driver versions and uploads all JARs to the GitHub release.

**Driver Versions Built:**
1. 23.26.0.0.0 (23ai - Latest)
2. 23.9.0.25.07 (23ai)
3. 23.3.0.23.09 (23c)
4. 21.20.0.0 (21c - Latest)
5. 21.13.0.0 (21c)
6. 21.1.0.0 (21c)
7. 19.29.0.0 (19c - Latest)
8. 19.21.0.0 (19c)
9. 18.15.0.0 (18c - Latest)
10. 12.2.0.1 (12c)

**Features:**
- Uses matrix strategy to build all versions in parallel
- Uploads each JAR as an artifact (retained for 7 days)
- Attaches all JARs to the GitHub release
- Automatically adds release notes with links to all JARs
- Can be triggered manually with custom version tag

---

## Creating a Release

### Automatic Build (Recommended)

1. Create and push a new tag:
   ```bash
   git tag v1.5.1
   git push origin v1.5.1
   ```

2. Go to GitHub → Releases → "Draft a new release"

3. Select the tag you just created (e.g., `v1.5.1`)

4. Fill in:
   - **Release title:** e.g., "Release 1.5.1"
   - **Description:** Your release notes

5. Click "Publish release"

6. The workflows will automatically:
   - Build all 10 driver versions
   - Upload all JARs to the release
   - Add comprehensive release notes

### Manual Trigger

If you need to rebuild a release or create JARs without creating a new release:

1. Go to: Actions → "Build and Release All Driver Versions"

2. Click "Run workflow"

3. Enter the version tag (e.g., `v1.5.1`)

4. Click "Run workflow"

The workflow will build all versions and attach them to the specified release tag.

---

## Workflow Outputs

After the workflow completes, the release will contain:

```
📦 Release v1.5.1
├── jdbc-tester-1.5.1-23.26.0.0.0.jar
├── jdbc-tester-1.5.1-23.9.0.25.07.jar
├── jdbc-tester-1.5.1-23.3.0.23.09.jar
├── jdbc-tester-1.5.1-21.20.0.0.jar
├── jdbc-tester-1.5.1-21.13.0.0.jar
├── jdbc-tester-1.5.1-21.1.0.0.jar
├── jdbc-tester-1.5.1-19.29.0.0.jar
├── jdbc-tester-1.5.1-19.21.0.0.jar
├── jdbc-tester-1.5.1-18.15.0.0.jar
└── jdbc-tester-1.5.1-12.2.0.1.jar
```

---

## Workflow Permissions

The workflows require the following permissions:

- `contents: write` - To upload release assets
- `packages: write` - To publish to GitHub Packages

These are automatically provided by the `GITHUB_TOKEN` secret.

---

## Troubleshooting

### Build fails for a specific driver version

If a build fails for a specific driver version:

1. Check if the driver version exists in Maven Central
2. Verify the profile ID in `pom.xml` matches the workflow
3. Check Maven Central for any availability issues

### Release assets not uploaded

Ensure the workflow has `contents: write` permission. This is configured in the workflow file and should work automatically.

### Manual workflow dispatch not working

Make sure you're using an existing tag/release. If the release doesn't exist, create it first on GitHub.

---

## Modifying the Workflow

### To add/remove driver versions:

Edit `.github/workflows/release-all-versions.yml`:

```yaml
strategy:
  matrix:
    driver-version:
      - { profile: 'ojdbc-X.X.X.X', version: 'X.X.X.X', label: 'Description' }
      # Add or remove entries here
```

### To change the JDK version:

Edit the `java-version` in the workflow:

```yaml
- name: Set up JDK 8
  uses: actions/setup-java@v4
  with:
    java-version: '8'  # Change this
```

---

## Related Documentation

- [BUILD_GUIDE.md](../../BUILD_GUIDE.md) - How to build locally
- [AVAILABLE_VERSIONS.md](../../AVAILABLE_VERSIONS.md) - All available driver versions
- [README.md](../../README.md) - Project documentation
