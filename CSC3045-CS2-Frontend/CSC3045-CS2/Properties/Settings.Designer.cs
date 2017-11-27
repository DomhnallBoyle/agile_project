using System;
using System.IO;

namespace CSC3045_CS2.Properties
{


    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.VisualStudio.Editors.SettingsDesigner.SettingsSingleFileGenerator", "11.0.0.0")]
    internal sealed partial class Settings : global::System.Configuration.ApplicationSettingsBase
    {

        private static Settings defaultInstance = ((Settings)(global::System.Configuration.ApplicationSettingsBase.Synchronized(new Settings())));
        public string AuthToken { get; internal set; }

        public static Settings Default
        {
            get
            {
                return defaultInstance;
            }
        }

        public string ProfileImageDirectory = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, @"..\..\profiles\");

        public string DefaultProfileImageFileExtension = ".jpg";

        public string DefaultProfileImage = "default.jpg";
    }
}
