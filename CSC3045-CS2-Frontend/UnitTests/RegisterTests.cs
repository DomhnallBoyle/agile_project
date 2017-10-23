using System;
using NUnit.Framework;
using CSC3045_CS2.Pages;
using RestSharp;
namespace UnitTests
{
    
    [TestFixture]
    [Register(RegisterState.STA)]
    public class RegisterTests
    {


        [Test]
        public void checkRequiredValuesTest()
        {
            Register a = new Register();
            a.checkPasswordsMatch();
            PasswordBox a = new PasswordBox();
        }
    }
}
