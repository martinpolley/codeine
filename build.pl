#!/usr/bin/perl

use warnings;
use strict;
use File::Basename;

my $script = __FILE__;
my $dir = dirname($script);
chdir $dir || die "cant cd to $dir";
$dir = `pwd`;
chomp($dir);
print "dir is $dir\n";
my $codeineDir = "$dir";
print "codeineDir is $codeineDir\n";
print "prepare codeine...\n";
my $propertiesFile = "$codeineDir/src/common/codeine/version.properties";
updateVersionFile();
es("rm -rf dist");
es("mkdir -p dist");
#es("bounce_minor_version.pl");

my $version = getVersionFull();
my $versionNoDate = getVersionNoDate();
print "java is $ENV{JAVA_HOME}\n";#1.7
print "ant is ant\n";#1.8?
es("cd $codeineDir ; ant", 1);
es("rsync -ur $codeineDir/deployment/* dist/");
#es("rsync -ur deployment/bin/* dist/bin/");
#es("cp $codeineDir/dist/bin/codeine.jar dist/bin/codeine.jar");
#es("rsync -ur deployment/project dist/");
#es("rsync -ur deployment/conf dist/");
es("echo '$ENV{BUILD_NUMBER}' > dist/build_number.txt");
my $tar = "codeine_".getVersionNoDate().".tar.gz";
es("cd dist; tar -czf ../$tar ./*");
print "tar is ready '$tar' for version $version\n";
my $zip = "codeine_".getVersionNoDate().".zip";
es("cd dist; zip -r ../$zip ./*");
print "zip is ready '$zip' for version $version\n";

unless ($ENV{'release-to-github'} eq "true") 
{
	exit(0);
} 
print "Will release new version to Github: $versionNoDate\n";
my $githubUser = $ENV{GITHUB_USER};
my $githubPassword = $ENV{GITHUB_PASSWORD};
my $res = r("curl -X POST -u $githubUser:$githubPassword -H \"Content-Type: application/json\" -d '{  \"tag_name\": \"v$versionNoDate\",  \"target_commitish\": \"master\",  \"name\": \"v$versionNoDate\",  \"body\": \"Codeine Offical Release\",  \"draft\": false,  \"prerelease\": true}' https://api.github.com/repos/Intel-IT/codeine/releases");
print "release returned: $res\n";
$res =~ /\"id\":\s([^,]*)/;
my $id = $1;
print "release id: $id\n";
es("curl -X POST -u $githubUser:$githubPassword -H \"Content-Type: application/zip\" --data-binary \"\@$zip\" \"https://uploads.github.com/repos/Intel-IT/codeine/releases/$id/assets?name=codeine.zip\"");

print "Done!";

sub es
{
	e(shift);
	my $status = $?;
	if ($status != 0)
	{
		print "error on execution, will exit.\n";
		exit $status >> 8;
	}
}
sub e
{
	my $cmd = shift;
	print "executing $cmd\n";
	system($cmd);
}
sub r
{
	my $cmd = shift;
	print "executing $cmd\n";
	return `$cmd`;
}
sub updateVersionFile
{
	my $major = getVersion('major');
	my $minor = getVersion('minor');
	my $build = $ENV{BUILD_NUMBER};
	my $date = getDate();
	es("echo 'CodeineVersion.build=$build\nCodeineVersion.major=$major\nCodeineVersion.minor=$minor\nCodeineVersion.date=$date\n' > $propertiesFile");
}
sub getVersionFull
{
	my $major = getVersion('major');
	my $minor = getVersion('minor');
	my $build = $ENV{BUILD_NUMBER} || getVersion('build');
	my $date = getDate();
	my $version = "$major.$minor.$build.$date";	
	return $version;
}
sub getVersionNoDate
{
	my $major = getVersion('major');
	my $minor = getVersion('minor');
	my $build = $ENV{BUILD_NUMBER} || getVersion('build');
	my $version = "$major.$minor.$build";	
	return $version;
}
sub getVersion
{
	my $key = shift;
	my $value = `cat $propertiesFile | grep $key | awk -F= '{print \$2}'`;
	chomp($value);
	return $value;
}

sub getDate
{
	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime time;
	$year += 1900;
	$mon  += 1;
	$mon = sprintf( "%02d", $mon );
	$mday = sprintf( "%02d", $mday );
	my $date = $year.$mon.$mday;
	return $date;
}


