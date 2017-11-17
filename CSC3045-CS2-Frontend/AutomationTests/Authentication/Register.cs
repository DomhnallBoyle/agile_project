using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;

namespace AutomationTests.Authentication
{
    [TestFixture]
    public class Register : BaseTestClass
    {
        [Test]
        public void RegisterValidCredentials()
        {
            Button registerButton = MainWindow.Get<Button>("LoginButton");
            registerButton.Click();

            Label registerPageTitle = MainWindow.Get<Label>("PageTitle");

            Assert.That(registerPageTitle.Text, Is.EqualTo("Registration"));
        }
    }
}
