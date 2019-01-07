var defaultURL = "http://localhost:8080/votingentities";
var currentURL = defaultURL;
var oldURL = defaultURL;

//var html = "";

$(document).ready
(
	() =>
	{
		//let menuBar = document.getElementsByClassName("menuBar")[0];
		//menuBar.innerHTML = "<button onclick='loadContent(\"" + defaultURL + "\")' type='button'>" + "Politicians" + "</button>";
		//menuBar.innerHTML += "<button on"
		
		//"<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Go to" + "</button>";
		
		loadContent(defaultURL)
	}
);

function goBack()
{
	loadContent(oldURL);
}

function loadContent(url)
{
	//oldContent = contentElement.innerHTML;
	oldURL = currentURL;
	currentURL = url;
	
	fetch(currentURL)
	.then(response => response.json())
	.then
	(
		content =>
		{
			let contentElement = document.getElementsByClassName("content")[0]
			//oldContent = contentElement.innerHTML;
			contentElement.innerHTML = "";
			let finalML = contentElement.innerHTML;
			
			finalML += appendContent(url, content);
			
			contentElement.innerHTML = finalML;
		}
	)
	.catch
	(
		error =>
		{
			console.error('An error ocurred', error);
		}
	)
}

function appendContent(url, property)
{
	let finalML = "<div class='object'>";
	
	Object.keys(property).forEach
	(
		(key, index) =>
		{
			if(typeof(property[key]) === "object")
			{
				finalML += "<div class='object'>";
				finalML += appendContent(url, property[key]);
				finalML += "</div>";
			}
			//else if(key !== "id")
			else if(!key.includes("id") && !key.includes("Id"))
			{
				finalML += "<div class='property'>";
				
				if(key === "name")
				{
					finalML += "<b>" + property[key] + "</b>";
				}
				else if(key === "href")
				{
					if(/votingentities\/\d/.test(property[key].substring(22)))
					//if(property[key].substring(22) === /votingentities\/\d\)
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Go to votingEntity" + "</button>";
					}
					else if(/issues\/\d_\d/.test(property[key].substring(22)))
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Go to issue" + "</button>";
					}					
					else if(property[key].substring(22) === "votingentities")
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "votingentities" + "</button>";
					}
					//else if(property[key].substring(22) === issues/votingentity=/\d/)
					else if(/issues\/_\d/.test(property[key].substring(22)))
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Issues" + "</button>";
					}
					else if(/stances\/\d\/\d/.test(property[key].substring(22)))
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Go to stances" + "</button>";
					}					

					
					//http://localhost:8080/votingentities
					//if(property[key] === url)
					//{
					//finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Refresh" + "</button>";
					//}
					//else if(/issues\/votingentity/.test(property[key].substring(21)))
					//{
						//finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Issues" + "</button>";
					//}
					//else if(/\d/.test(property[key].substring(21)))
					//{
						//finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Go to" + "</button>";
					//}
					//else
					//{
						//finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Back to All" + "</button>";
					//}
				}
				else if(key === "templated")
				{
					
				}
				else
				{
					finalML += key + ": " + property[key];
				}
				
				finalML += "</div>";
			}
		}
	);
	
	finalML += "</div>";
	return finalML;
}