import argparse, os, subprocess, zipfile, shutil
from pathlib import Path
p=argparse.ArgumentParser()
p.add_argument('--compiler',required=True)
p.add_argument('--bukkit',required=True)
p.add_argument('--skript',required=True)
a=p.parse_args()
r=Path(__file__).resolve().parent
out=r/'build/classes'
if out.exists(): shutil.rmtree(out)
out.mkdir(parents=True)
subprocess.run(['java','-jar',str(Path(a.compiler).resolve()),'-17','-nowarn','-cp',os.pathsep.join(str(Path(x).resolve()) for x in [a.bukkit,a.skript]),'-d',str(out),str(r/'src/main/java')],check=True)
jar=r/'dist/MMOSkriptBridge-1.1.0-beta.3.jar'
jar.parent.mkdir(exist_ok=True)
with zipfile.ZipFile(jar,'w',zipfile.ZIP_DEFLATED) as z:
 z.writestr('META-INF/MANIFEST.MF','Manifest-Version: 1.0\n\n')
 for f in sorted(out.rglob('*.class')):z.write(f,f.relative_to(out))
 z.write(r/'src/main/resources/plugin.yml','plugin.yml')
print(jar)
