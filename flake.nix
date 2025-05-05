{
  description = "A Nix-flake-based Clojure development environment";

  inputs.nixpkgs.url = "https://flakehub.com/f/NixOS/nixpkgs/0.1";

  outputs = inputs:

    let
      javaVersion = 23; # Change this value to update the whole stack

      supportedSystems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forEachSupportedSystem = f: inputs.nixpkgs.lib.genAttrs supportedSystems (system: f {
        pkgs = import inputs.nixpkgs {
          inherit system;
          overlays = [ inputs.self.overlays.default ];
        };
      });
    in
    {
      overlays.default =
        final: prev:
        let
          jdk = prev."jdk${toString javaVersion}";
        in
        {
          inherit jdk;
          boot = prev.boot.override { inherit jdk; };
          clojure = prev.clojure.override { inherit jdk; };
          leiningen = prev.leiningen.override { inherit jdk; };
          maven = prev.maven.override { jdk_headless = jdk; };
          gradle = prev.gradle.override { java = jdk; };
          lombok = prev.lombok.override { inherit jdk; };
        };

      devShells = forEachSupportedSystem ({ pkgs }: {
        default = pkgs.mkShell {
          packages = with pkgs; [
            boot
            clojure
            leiningen
            gcc
            gradle
            jdk
            maven
            ncurses
            patchelf
            zlib
            jdt-language-server
            jetbrains.idea-community-bin
          ];
        };
        shellHook =
          let
            loadLombok = "-javaagent:${pkgs.lombok}/share/java/lombok.jar";
            prev = "\${JAVA_TOOL_OPTIONS:+ $JAVA_TOOL_OPTIONS}";
          in
            ''
              export JAVA_TOOL_OPTIONS="${loadLombok}${prev}"
            '';
      });
    };
}
