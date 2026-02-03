# Release Guide

This guide explains how to create new releases with automated builds for all Oracle JDBC driver versions.

## Quick Start: Creating a New Release

### Step 1: Update Version in pom.xml

Edit `pom.xml` and update the version number:

```xml
<version>1.5.1</version>  <!-- Change this -->
```

### Step 2: Commit and Tag

```bash
git add pom.xml
git commit -m "Bump version to 1.5.1"
git tag v1.5.1
git push origin master
git push origin v1.5.1
```

### Step 3: Create Release on GitHub

1. Go to: https://github.com/bernalvarela/oracle-jdbc-tester/releases/new

2. Fill in the form:
   - **Choose a tag:** Select `v1.5.1` (the tag you just pushed)
   - **Release title:** `Release 1.5.1`
   - **Description:** Use the [RELEASE_TEMPLATE.md](.github/RELEASE_TEMPLATE.md) as a starting point

3. Click **"Publish release"**

### Step 4: Wait for Automation

The GitHub Actions workflow will automatically:
- ✅ Build 10 versions in parallel
- ✅ Upload all JARs to the release
- ✅ Add detailed release notes

**Build time:** ~5-10 minutes

## What Gets Built

Each release includes **10 JARs** with different Oracle JDBC driver versions:

| JAR Filename Pattern | Oracle DB | Driver Version |
|---------------------|-----------|----------------|
| `jdbc-tester-X.X.X-23.26.0.0.0.jar` | 23ai | Latest |
| `jdbc-tester-X.X.X-23.9.0.25.07.jar` | 23ai | Stable |
| `jdbc-tester-X.X.X-23.3.0.23.09.jar` | 23c | Stable |
| `jdbc-tester-X.X.X-21.20.0.0.jar` | 21c | Latest |
| `jdbc-tester-X.X.X-21.13.0.0.jar` | 21c | Stable |
| `jdbc-tester-X.X.X-21.1.0.0.jar` | 21c | Initial |
| `jdbc-tester-X.X.X-19.29.0.0.jar` | 19c | Latest |
| `jdbc-tester-X.X.X-19.21.0.0.jar` | 19c | Stable |
| `jdbc-tester-X.X.X-18.15.0.0.jar` | 18c | Latest |
| `jdbc-tester-X.X.X-12.2.0.1.jar` | 12c | Latest |

## Manual Workflow Trigger

If you need to rebuild a release or add JARs to an existing release:

1. Go to: **Actions** → **"Build and Release All Driver Versions"**

2. Click **"Run workflow"**

3. Enter the version tag (e.g., `v1.5.1`)

4. Click **"Run workflow"**

This will rebuild all JARs and attach them to the specified release.

## Release Checklist

Before creating a release, ensure:

- [ ] Version number updated in `pom.xml`
- [ ] `CHANGELOG.md` or release notes prepared (optional)
- [ ] All changes committed and pushed to master
- [ ] Tag created and pushed
- [ ] Release notes ready (can use [RELEASE_TEMPLATE.md](.github/RELEASE_TEMPLATE.md))

After release is published:

- [ ] Wait for GitHub Actions to complete (~10 minutes)
- [ ] Verify all 10 JARs are attached to the release
- [ ] Test download and execution of at least one JAR
- [ ] Announce the release (if applicable)

## Troubleshooting

### Workflow doesn't start

- Ensure the tag is pushed: `git push origin v1.5.1`
- Check the workflow file exists: `.github/workflows/release-all-versions.yml`
- Verify you published the release (not just saved as draft)

### Build fails for a specific driver version

- Check the Actions tab for error logs
- Verify the driver version exists in Maven Central
- The workflow will continue building other versions even if one fails

### JARs not attached to release

- Check workflow permissions in repository settings
- Ensure `GITHUB_TOKEN` has `contents: write` permission
- Re-run the workflow manually if needed

## Advanced: Customizing Workflow

To add or remove driver versions from automated builds:

Edit `.github/workflows/release-all-versions.yml`:

```yaml
strategy:
  matrix:
    driver-version:
      - { profile: 'ojdbc-23.26.0.0.0', version: '23.26.0.0.0', label: '23ai-Latest' }
      # Add or modify entries here
```

See [Workflow Documentation](.github/workflows/README.md) for more details.

## Related Documentation

- [BUILD_GUIDE.md](BUILD_GUIDE.md) - Local build instructions
- [AVAILABLE_VERSIONS.md](AVAILABLE_VERSIONS.md) - All 73 available versions
- [.github/workflows/README.md](.github/workflows/README.md) - Workflow documentation
- [.github/RELEASE_TEMPLATE.md](.github/RELEASE_TEMPLATE.md) - Release notes template

---

**Questions?** Open an issue at https://github.com/bernalvarela/oracle-jdbc-tester/issues
